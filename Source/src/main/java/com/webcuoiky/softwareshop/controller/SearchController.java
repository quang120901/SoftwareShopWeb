package com.webcuoiky.softwareshop.controller;

import com.webcuoiky.softwareshop.model.Order_items;
import com.webcuoiky.softwareshop.model.Software;
import com.webcuoiky.softwareshop.repository.SoftwareRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

    @Autowired
    private SoftwareRepository repo;

    @RequestMapping("/software/search")
    public String searchSoftware(Model model,
                                 @RequestParam String category,
                                 HttpSession session,
                                 @RequestParam String name) {

        Map<Integer, Order_items> cart = (Map<Integer, Order_items>) session.getAttribute("cart");

        if(cart == null) {
            cart = new HashMap<>();
        }

        String subtotal = calculateTotal(cart);
        int totalQuantity = calculateTotalQuantity(cart);

        model.addAttribute("cart", cart != null ? cart.values() : null);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("totalQuantity", totalQuantity);

        if (category==null || category.isEmpty()){
            if(name==null || name.isEmpty()){
                model.addAttribute("softwareSearch", repo.findAll());
                return "software/softwares_search";
            }
            else{
                List<Software> softwareSearch = repo.findByNameContaining(name);
                model.addAttribute("softwareSearch", softwareSearch);
                return "software/softwares_search";
            }
        }
        List<Software> softwareSearch = repo.findByNameContainingAndCategory(name, category);
        model.addAttribute("softwareSearch", softwareSearch);
        return "software/softwares_search";



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
        DecimalFormat df = new DecimalFormat("0.000");
        return df.format(total);
    }


    public int calculateTotalQuantity(Map<Integer, Order_items> cart) {
        int totalQuantity = 0;
        for (Order_items item : cart.values()) {
            totalQuantity += item.getSoftware().getQuantity();
        }
        return totalQuantity;
    }


}
