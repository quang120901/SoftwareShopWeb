package com.webcuoiky.softwareshop.controller;

import com.webcuoiky.softwareshop.model.Order_items;
import com.webcuoiky.softwareshop.model.Software;

import com.webcuoiky.softwareshop.model.User;
import com.webcuoiky.softwareshop.repository.SoftwareRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {

    @Autowired
    SessionFactory factory;

    @Autowired
    private SoftwareRepository softwareRepository;

    @PostMapping("softwares/addToCart")
    public String addToCart(@RequestParam("id") Integer id,
                            @RequestParam("name") String name,
                            @RequestParam("image") String image,
                            @RequestParam("price") Float price,
                            @RequestParam(value = "sale_price", required = false) Float sale_price,
                            @RequestParam("quantity") Integer quantity,
                            HttpSession session,
                            Model model,
                            HttpServletRequest request) {

        Map<Integer, Order_items> cart = (Map<Integer, Order_items>) session.getAttribute("cart");

        if(cart == null) {
            cart = new HashMap<>();
        }

        Float priceToCharge = sale_price != null ? sale_price : price;

        if (cart.containsKey(id)) {
            Order_items existingItem = cart.get(id);
            existingItem.getSoftware().setQuantity(existingItem.getSoftware().getQuantity() + quantity);
        } else {
            Order_items newItem = new Order_items();
            Software newSoftware = new Software();
            newSoftware.setId(id);
            newSoftware.setName(name);
            newSoftware.setImage(image);
            newSoftware.setPrice(priceToCharge);
            newSoftware.setSale_price(sale_price);
            newSoftware.setQuantity(quantity);
            newItem.setSoftware(newSoftware);
            cart.put(id, newItem);
        }

        String subtotal = calculateTotal(cart);
        int totalQuantity = calculateTotalQuantity(cart);

        session.setAttribute("cart", cart);

        model.addAttribute("cart", cart.values());
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("totalQuantity", totalQuantity);

        List<Software> softwares = softwareRepository.findAll();
        model.addAttribute("softwares", softwares);

        String referer = request.getHeader("Referer");

        if (referer != null) {
            return "redirect:" + referer;
        }

        return "redirect:/softwares";
    }

    public String calculateTotal(Map<Integer, Order_items> orderItems) {
        double total = 0.0;
        if (orderItems != null) {
            for (Order_items item : orderItems.values()) {
                if (item.getSoftware() != null) {
                    total += item.getSoftware().getPrice() * item.getSoftware().getQuantity();
                }
            }
        }

        DecimalFormatSymbols customSymbol = new DecimalFormatSymbols();
        customSymbol.setGroupingSeparator('.');//Đổi dấu "," thành "."

        DecimalFormat integerFormat = new DecimalFormat("#,###", customSymbol);
        String formatedInteger = integerFormat.format(total);

        return formatedInteger + ".000"; //1000.000 -> 1.000.000
    }


    public int calculateTotalQuantity(Map<Integer, Order_items> cart) {
        int totalQuantity = 0;
        for (Order_items item : cart.values()) {
            totalQuantity += item.getSoftware().getQuantity();
        }
        return totalQuantity;
    }


    @PostMapping("softwares/removeFromCart")
    public String removeFromCart(@RequestParam("id") Integer id, HttpSession session, Model model, HttpServletRequest request) {

        Map<Integer, Order_items> cart = (Map<Integer, Order_items>) session.getAttribute("cart");

        if (cart != null && cart.containsKey(id)) {
            cart.remove(id);
            session.setAttribute("cart", cart);

            String subtotal = calculateTotal(cart);
            int totalQuantity = calculateTotalQuantity(cart);

            model.addAttribute("cart", cart.values());
            model.addAttribute("subtotal", subtotal);
            model.addAttribute("totalQuantity", totalQuantity);
        }

        List<Software> softwares = softwareRepository.findAll();
        model.addAttribute("softwares", softwares);


        String referer = request.getHeader("Referer");

        if (referer != null) {
            return "redirect:" + referer;
        }

        return "software/softwares";
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        Map<Integer, Order_items> cart = (Map<Integer, Order_items>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            return "redirect:/";
        }

        String subtotal = calculateTotal(cart);
        int totalQuantity = calculateTotalQuantity(cart);

        model.addAttribute("cart", cart.values());
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("totalQuantity", totalQuantity);

        return "software/checkout";
    }

    @PostMapping("/updateQuantity")
    public String updatesoftwareQuantity(@RequestParam("id") Long softwareId,
                                        @RequestParam(value = "increase_software_quantity_btn", required = false) String increaseBtn,
                                        @RequestParam(value = "decrease_software_quantity_btn", required = false) String decreaseBtn,
                                        Model model,
                                        HttpServletRequest request) {

        // Retrieve the cart from the session
        Map<Long, Order_items> cart = (Map<Long, Order_items>) request.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }


        Order_items cartItem = cart.get(softwareId);
        if (cartItem != null) {
            if (increaseBtn != null) {
                cartItem.setOrder_quantity(cartItem.getOrder_quantity() + 1);
            } else if (decreaseBtn != null && cartItem.getOrder_quantity() > 1) {
                cartItem.setOrder_quantity(cartItem.getOrder_quantity() - 1);
            }
        } else {

            Software software = (Software) factory.openSession().get(Software.class, softwareId);
            cartItem = new Order_items();
            cartItem.setSoftware(software);
            cartItem.setOrder_quantity(1);
            cart.put(softwareId, cartItem);
        }


        Software software = (Software) factory.openSession().get(Software.class, softwareId);
        model.addAttribute("software_detail", software);
        model.addAttribute("order_items", cartItem);


        request.getSession().setAttribute("cart", cart);

        return "software/software_detail";
    }



    @RequestMapping("/thankyou")
    public String thankyou() {
        return "software/thankyou";
    }






}
