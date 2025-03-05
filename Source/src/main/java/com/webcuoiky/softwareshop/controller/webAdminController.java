package com.webcuoiky.softwareshop.controller;

import com.webcuoiky.softwareshop.model.Software;
import com.webcuoiky.softwareshop.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("admin")
public class webAdminController {

    @Autowired
    SessionFactory factory;

    @RequestMapping("")
    public  String admin() {

        return "adminPage/admin";
    }


    @RequestMapping("/admin_user_list")
    public  String adminUserList() {

        return "adminPage/admin_user_list";
    }

    @ModelAttribute("users")
    public List<User> getUsers() {
        Session session = factory.openSession();
        String hql ="FROM User";
        Query query = session.createQuery(hql);
        List<User> list = query.list();
        return list;
    }


    @PostMapping("/changeRole")
    public String changeRole(@RequestParam("userId") Long userId, @RequestParam("role") String role) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            User user = session.get(User.class, userId);

            if (user != null) {
                user.setRole(role);
                session.update(user);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return "redirect:/admin/admin_user_list";
    }

//    @RequestMapping("/product-list/update-product")
//    public  String productUpdate() {
//
//        return "adminPage/admin_update_product";
//    }
//    @RequestMapping("/product-list/add-product")
//    public  String productAdd() {
//
//        return "adminPage/admin_add_product";
//    }
//    @RequestMapping("/category")
//    public  String category_list() {
//
//        return "adminPage/admin_category";
//    }
//    @RequestMapping("/product-list")
//    public  String product_list() {
//
//        return "adminPage/admin_product_list";
//    }
}
