package com.example.contactosApp.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String loginPage(@RequestParam Optional<String> error,
                        @RequestParam Optional<String> logout,
                        Model model) {
        error.ifPresent(e -> model.addAttribute("error", true));
        logout.ifPresent(l -> model.addAttribute("logout", true));
        return "login";
    }
}
