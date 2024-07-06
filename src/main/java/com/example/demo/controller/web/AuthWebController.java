package com.example.demo.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthWebController {

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }
    
    @GetMapping("/logout")
    public String showLogoutForm(Model model) {
        return "logout";
    }
}
