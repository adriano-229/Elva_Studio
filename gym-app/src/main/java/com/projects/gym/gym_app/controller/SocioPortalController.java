package com.projects.gym.gym_app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/socio")
public class SocioPortalController {

    @GetMapping("/portal")
    public String portal(Authentication authentication, Model model) {
        String username = authentication != null ? authentication.getName() : "socio";
        model.addAttribute("titulo", "Portal de Socios");
        model.addAttribute("seccion", "Socios");
        model.addAttribute("usuarioActual", username);
        model.addAttribute("active", "socio-portal");
        return "socio/portal";
    }

    @GetMapping("/rutinas")
    public String rutinas(Authentication authentication, Model model) {
        String username = authentication != null ? authentication.getName() : "socio";
        model.addAttribute("titulo", "Mis rutinas");
        model.addAttribute("seccion", "Socios");
        model.addAttribute("usuarioActual", username);
        model.addAttribute("active", "socio-rutinas");
        return "socio/rutinas";
    }

    @GetMapping("/deuda")
    public String deuda(Authentication authentication, Model model) {
        String username = authentication != null ? authentication.getName() : "socio";
        model.addAttribute("titulo", "Mi deuda");
        model.addAttribute("seccion", "Socios");
        model.addAttribute("usuarioActual", username);
        model.addAttribute("active", "socio-deuda");
        return "socio/deuda";
    }
}
