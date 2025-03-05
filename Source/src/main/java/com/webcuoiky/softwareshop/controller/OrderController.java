package com.webcuoiky.softwareshop.controller;

import com.webcuoiky.softwareshop.model.Order;
import com.webcuoiky.softwareshop.model.Order_items;
import com.webcuoiky.softwareshop.model.User;
import com.webcuoiky.softwareshop.repository.OrderItemsRepository;
import com.webcuoiky.softwareshop.repository.OrderRepository;
import com.webcuoiky.softwareshop.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/thankyou")
    public String placeOrder(@RequestParam("address") String address,
                             @RequestParam("name") String name,
                             HttpSession session, Model model) {

        Map<Integer, Order_items> cart = (Map<Integer, Order_items>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            return "redirect:/";
        }

        session.setAttribute("address", address);
        session.setAttribute("name", name);
        Float cost = calculateTotalFloat(cart);
        session.setAttribute("subtotal", cost);


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = null;

        if (auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String)) {
            String email = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
            user = userRepository.findByEmail(email);
        }

        if (user != null) {
            String email = user.getEmail();
            String phone = user.getPhone_number();
            String status = "not paid";
            String date = java.time.LocalDate.now().toString();

            Order order = new Order();
            order.setName(name);
            order.setEmail(email);
            order.setPhone(phone);
            order.setAddress(address);
            order.setCost(cost);
            order.setStatus(status);
            order.setDate(java.sql.Date.valueOf(date));

            order.setUser(user);

            Order savedOrder = orderRepository.save(order);

            for (Order_items cartItem : cart.values()) {
                Order_items orderItem = new Order_items();
                orderItem.setOrder(savedOrder);
                orderItem.setSoftware(cartItem.getSoftware());
                orderItem.setOrder_quantity(cartItem.getSoftware().getQuantity());

                orderItemsRepository.save(orderItem);
            }

            return "software/thankyou";
        }

        return "redirect:/login";
    }

    public Float calculateTotalFloat(Map<Integer, Order_items> orderItems) {
        float totalFloat = 0.0F;
        if (orderItems != null) {
            for (Order_items item : orderItems.values()) {
                if (item.getSoftware() != null) {
                    totalFloat += item.getSoftware().getPrice() * item.getSoftware().getQuantity();
                }
            }
        }
        return totalFloat;
    }


    @RequestMapping("/user-order")
    public String userOder(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = null;

        if (auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String)) {
            String email = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
            user = userRepository.findByEmail(email);
        }

        List<Order> orders = orderRepository.findByUser(user);
        model.addAttribute("orders", orders);

        return "user_order";
    }

    @RequestMapping("/user-order-detail/{orderId}")
    public String orderDetail(@PathVariable("orderId") Integer orderId, Model model) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            List<Order_items> orderItems = orderItemsRepository.findByOrder(order);

            model.addAttribute("order", order); // Add order to the model

            model.addAttribute("orderItems", orderItems);
        }
        return "user_order_detail";
    }
}
