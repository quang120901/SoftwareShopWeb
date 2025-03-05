package com.webcuoiky.softwareshop.controller;

import com.webcuoiky.softwareshop.model.Order_items;
import com.webcuoiky.softwareshop.model.Software;
import com.webcuoiky.softwareshop.repository.SoftwareRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value={"/","/index"})
public class IndexController
{
    @Autowired
    private SoftwareRepository repo;

    @GetMapping
    public String showOffice(HttpSession session, Model model) {

        ////////Tab phần mềm mới//////////
        List<Software> softwareListOffice;
        softwareListOffice = repo.findByCategory("office");
        List<Software> softwareListGraphic = repo.findByCategory("graphic");
        List<Software> softwareListSound = repo.findByCategory("sound");
        List<Software> softwareListOther = repo.findByCategory("other");

        softwareListOffice.sort(Comparator.comparingInt(Software::getId).reversed());
        softwareListSound.sort(Comparator.comparingInt(Software::getId).reversed());
        softwareListGraphic.sort(Comparator.comparingInt(Software::getId).reversed());
        softwareListOther.sort(Comparator.comparingInt(Software::getId).reversed());

        int limit = 6;
        if (softwareListOffice.size() > limit) {
            softwareListOffice = softwareListOffice.subList(0, limit);
        }
        if (softwareListGraphic.size() > limit) {
            softwareListGraphic = softwareListGraphic.subList(0, limit);
        }

        if (softwareListSound.size() > limit) {
            softwareListSound = softwareListSound.subList(0, limit);
        }

        if (softwareListOther.size() > limit) {
            softwareListOther = softwareListOther.subList(0, limit);
        }

        model.addAttribute("softwareOffice", softwareListOffice);
        model.addAttribute("softwareGraphic", softwareListGraphic);
        model.addAttribute("softwareSound", softwareListSound);
        model.addAttribute("softwareOther", softwareListOther);

        System.out.println("Số sản phẩm Office lấy được: " + softwareListOffice.size());
        ////////////////

        /////////Tab sản phẩm nổi bật miễn phí (miễn phi + mới nhất)
        List<Software> softwareListFree = repo.findByPrice(0.000);
        softwareListFree.sort(Comparator.comparingInt(Software::getId).reversed());
        int limit1 = 3;
        int limit2 = 6;
        List<Software> softwareListFree1;
        List<Software> softwareListFree2 = List.of();
        if (softwareListFree.size() > limit1) {
            softwareListFree1 = softwareListFree.subList(0, limit1); //sort xong lấy t 0 tới limit1, giảm số
            softwareListFree2 = softwareListFree.subList(limit1, Math.min(limit2,softwareListFree.size())); //Nếu list > 6 thì lấy 6 = limit2, nếu không thì lấy list.size()
        }
        else{
            softwareListFree1 = softwareListFree;       //ít hơn limit1 thì lấy hết + copy sang cột 2
            softwareListFree2 = softwareListFree;
        }

        System.out.println("Số sản phẩm Free lấy được: " + softwareListFree.size() +"  " +softwareListFree2.size());
        model.addAttribute("softwareFree1", softwareListFree1);
        model.addAttribute("softwareFree2", softwareListFree2);
        /////////
        System.out.println("Check");
        /////////Tab sản phẩm nổi bật mất phí (mất phí + mắc nhất)
        List<Software> softwareListPaid = repo.findByPriceNot(0.000);
        softwareListPaid.sort(Comparator.comparing(Software::getPrice).reversed());

        List<Software> softwareListPaid1;
        List<Software> softwareListPaid2 = List.of();
        if (softwareListPaid.size() > limit1) {
            softwareListPaid1 = softwareListPaid.subList(0, limit1); //sort xong lấy t 0 tới limit1, giảm số
            softwareListPaid2 = softwareListPaid.subList(limit1, Math.min(limit2,softwareListPaid.size())); //Nếu list > 6 thì lấy 6 = limit2, nếu không thì lấy list.size()
        }
        else{
            softwareListPaid1 = softwareListPaid;       //ít hơn limit1 thì lấy hết + copy sang cột 2
            softwareListPaid2 = softwareListPaid;
        }

        System.out.println("Số sản phẩm Paid lấy được: " + softwareListPaid.size() +"  " +softwareListPaid2.size());
        model.addAttribute("softwarePaid1", softwareListPaid1);
        model.addAttribute("softwarePaid2", softwareListPaid2);



        Map<Integer, Order_items> cart = (Map<Integer, Order_items>) session.getAttribute("cart");

        if(cart == null) {
            cart = new HashMap<>();
        }

        String subtotal = calculateTotal(cart);
        int totalQuantity = calculateTotalQuantity(cart);

        model.addAttribute("cart", cart != null ? cart.values() : null);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("totalQuantity", totalQuantity);





        return "index";
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
