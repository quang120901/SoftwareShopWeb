package com.webcuoiky.softwareshop.controller;

import com.webcuoiky.softwareshop.model.Order_items;
import com.webcuoiky.softwareshop.model.Software;
import com.webcuoiky.softwareshop.repository.SoftwareRepository;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SoftwaresController {

    @Autowired
    SessionFactory factory;

    @Autowired
    private SoftwareRepository repo;

    @RequestMapping("softwares")
    public String showSoftwares(HttpSession session, ModelMap model) {
        Map<Integer, Order_items> cart = (Map<Integer, Order_items>) session.getAttribute("cart");

        if(cart == null) {
            cart = new HashMap<>();
        }

        String subtotal = calculateTotal(cart);
        int totalQuantity = calculateTotalQuantity(cart);

        model.addAttribute("cart", cart != null ? cart.values() : null);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("totalQuantity", totalQuantity);

        //Để đánh dấu cho navigation bar
        model.addAttribute("category", "softwares");//==allsoft


        return "software/softwares";
    }

    @ModelAttribute("softwares")
    public List<Software> getSoftwares() {
        Session session = factory.openSession();
        String hql ="FROM Software";
        Query query = session.createQuery(hql);
        List<Software> list = query.list();
        return list;
    }

    @RequestMapping("/software_detail/{id}")
    public String softwareDetail(@PathVariable ("id") int id,
                                 HttpSession session,
                                 ModelMap model) {

        Map<Integer, Order_items> cart = (Map<Integer, Order_items>) session.getAttribute("cart");

        if(cart == null) {
            cart = new HashMap<>();
        }

        String subtotal = calculateTotal(cart);
        int totalQuantity = calculateTotalQuantity(cart);

        model.addAttribute("cart", cart != null ? cart.values() : null);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("totalQuantity", totalQuantity);


        try{
            Software software = (Software) factory.openSession().get(Software.class, id);
            model.addAttribute("software_detail", software);

            String category = software.getCategory();
            System.out.println("//////"+category);

            List<Software> softwareListRelated = repo.findByCategory(category);
            softwareListRelated.sort(Comparator.comparing(Software::getId).reversed());
            int limit = 4;
            if (softwareListRelated.size() > limit) {
                softwareListRelated = softwareListRelated.subList(0, limit);
            }
            model.addAttribute("softwareListRelated", softwareListRelated);

            return "software/software_detail";
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return "error";
        }
        finally{
            factory.openSession().close();
        }
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
