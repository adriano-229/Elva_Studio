package com.projects.gym.gym_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(@RequestParam Optional<String> error,
                        @RequestParam Optional<String> logout,
                        Model model) {
        error.ifPresent(e -> model.addAttribute("error", true));
        logout.ifPresent(l -> model.addAttribute("logout", true));
        return "login";
    }
}
