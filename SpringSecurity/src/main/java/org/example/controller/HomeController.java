package org.example.controller;//package com.nhnacademy.security.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.util.Objects;
//
//@Controller
//public class HomeController {
//    @GetMapping("/")
//    public String home(HttpServletRequest httpServletRequest, Model model){
//        HttpSession session = httpServletRequest.getSession(false);
//        if(Objects.isNull(session)){
//            return "redirect:/login";
//        }else {
//            model.addAttribute("id",session.getAttribute("id"));
//            return "home";
//        }
//    }
//}
