package com.webcuoiky.softwareshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;


//http:/www.thymeleaf.com
//localhost:8080/
@Controller
public class webBasicController {
    @RequestMapping(value={"/","/index"})
    public String index(Locale locale, Model model) {
        String currentLang = locale.getLanguage();
        model.addAttribute("currentLang", currentLang);
        return "index";
    }
//    @RequestMapping("/checkout")
//    public String checkout() {
//
//        return "checkout";
//    }
//    @RequestMapping("/product")
//    public String product() {
//
//        return "product";
//    }
//    @RequestMapping("/category")
//    public String category() {
//
//        return "category";
//    }
//    @RequestMapping("/login")
//    public String login() {
//
//        return "login";
//    }
//    @RequestMapping("/register")
//    public String regis() {
//
//        return "register";
//    }
//    @RequestMapping("/user_order")
//    public String check() {
//
//        return "user_order";
//    }
//    @RequestMapping("/software/search")
//    public String softSearch() {
//
//        return "software/softwares_search";
//    }
}
