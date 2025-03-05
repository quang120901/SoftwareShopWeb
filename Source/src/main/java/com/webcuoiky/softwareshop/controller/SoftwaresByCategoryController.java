package com.webcuoiky.softwareshop.controller;

import com.webcuoiky.softwareshop.model.Order_items;
import com.webcuoiky.softwareshop.model.Software;
import com.webcuoiky.softwareshop.repository.SoftwareRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;
import java.util.*;

@Controller
@RequestMapping("/softwares")
public class SoftwaresByCategoryController {

    @Autowired
    private SoftwareRepository repo;

    @GetMapping("/office")
    public String showOffice(HttpSession session , Model model) {
        List<Software> softwareList = repo.findByCategory("office");
        model.addAttribute("category", "office");
        model.addAttribute("softwares", softwareList);

        Map<Integer, Order_items> cart = (Map<Integer, Order_items>) session.getAttribute("cart");

        if(cart == null) {
            cart = new HashMap<>();
        }

        String subtotal = calculateTotal(cart);
        int totalQuantity = calculateTotalQuantity(cart);

        model.addAttribute("cart", cart != null ? cart.values() : null);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("totalQuantity", totalQuantity);



        return "software/softwares";
    }

    @GetMapping("/graphic")
    public String showGraphic(HttpSession session, Model model) {
        List<Software> softwareList = repo.findByCategory("graphic");
        model.addAttribute("category", "graphic");
        model.addAttribute("softwares", softwareList);




        Map<Integer, Order_items> cart = (Map<Integer, Order_items>) session.getAttribute("cart");

        if(cart == null) {
            cart = new HashMap<>();
        }

        String subtotal = calculateTotal(cart);
        int totalQuantity = calculateTotalQuantity(cart);

        model.addAttribute("cart", cart != null ? cart.values() : null);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("totalQuantity", totalQuantity);




        return "software/softwares";
    }

    @GetMapping("/sound")
    public String showSound(HttpSession session, Model model) {
        List<Software> softwareList = repo.findByCategory("sound");
        model.addAttribute("category", "sound");
        model.addAttribute("softwares", softwareList);



        Map<Integer, Order_items> cart = (Map<Integer, Order_items>) session.getAttribute("cart");

        if(cart == null) {
            cart = new HashMap<>();
        }

        String subtotal = calculateTotal(cart);
        int totalQuantity = calculateTotalQuantity(cart);

        model.addAttribute("cart", cart != null ? cart.values() : null);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("totalQuantity", totalQuantity);





        return "software/softwares";
    }

    @GetMapping("/other")
    public String showOther(HttpSession session, Model model) {
        List<Software> softwareList = repo.findByCategory("other");
        model.addAttribute("category", "other");
        model.addAttribute("softwares", softwareList);


        Map<Integer, Order_items> cart = (Map<Integer, Order_items>) session.getAttribute("cart");

        if(cart == null) {
            cart = new HashMap<>();
        }

        String subtotal = calculateTotal(cart);
        int totalQuantity = calculateTotalQuantity(cart);

        model.addAttribute("cart", cart != null ? cart.values() : null);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("totalQuantity", totalQuantity);





        return "software/softwares";
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
