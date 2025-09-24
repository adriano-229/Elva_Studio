package com.projects.gym.gym_app.controller;

import com.projects.gym.gym_app.domain.DetalleDiario;
import com.projects.gym.gym_app.domain.Rutina;
import com.projects.gym.gym_app.domain.Socio;
import com.projects.gym.gym_app.repository.SocioRepository;
import com.projects.gym.gym_app.service.RutinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/socio")
@RequiredArgsConstructor
public class SocioPortalController {

    private final RutinaService rutinaService;
    private final SocioRepository socioRepository;

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
        String username = authentication != null ? authentication.getName() : null;
        model.addAttribute("titulo", "Mis rutinas");
        model.addAttribute("seccion", "Socios");
        model.addAttribute("usuarioActual", username);
        model.addAttribute("active", "socio-rutinas");

        if (username == null) {
            model.addAttribute("rutinaNoEncontrada", true);
            return "socio/rutinas";
        }

        Socio socio = socioRepository.findByUsuario_NombreUsuarioAndEliminadoFalse(username)
                .orElse(null);
        if (socio == null) {
            model.addAttribute("rutinaNoEncontrada", true);
            return "socio/rutinas";
        }

        Rutina rutina = rutinaService.buscarRutinaActual(socio.getNumeroSocio());
        if (rutina == null) {
            model.addAttribute("rutinaNoEncontrada", true);
            return "socio/rutinas";
        }

        List<DetalleDiario> detalles = rutina.getDetallesDiarios() != null
                ? rutina.getDetallesDiarios().stream()
                .sorted(Comparator.comparingInt(DetalleDiario::getNumeroDia))
                .toList()
                : List.of();

        model.addAttribute("rutina", rutina);
        model.addAttribute("detalles", detalles);
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
