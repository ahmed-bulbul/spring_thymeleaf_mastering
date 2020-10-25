package com.thikthak.app.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({ "/", "/admin" })
public class DashboardController {

    @Secured("ROLE_USER")
    @RequestMapping("/")
    public String dashboard(Model model){

        SecurityContext securityContext = SecurityContextHolder.getContext();
        System.out.println(securityContext.getAuthentication().getName());

        model.addAttribute("movies", "test");
        return "dashboard";
    }

    @GetMapping("/welcome2")
    public String printHello(){
        return ("<h1> Welcome </h1>");
    }

}
