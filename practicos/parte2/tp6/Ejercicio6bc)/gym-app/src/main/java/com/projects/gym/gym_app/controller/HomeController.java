package com.projects.gym.gym_app.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class HomeController {
    @GetMapping("")
  public String home(Model model) {
    model.addAttribute("titulo", "Bienvenido");
    model.addAttribute("seccion", "Dashboard");
    model.addAttribute("active", "home");
    model.addAttribute("ahora", LocalDateTime.now());

    model.addAttribute("metricas", Map.of(
        "sociosActivos", 125,
        "cuotasPendientes", 18,
        "ingresosMes", "$ 1.250.000",
        "notificaciones", 3
    ));

    record Accion(String nombre, String usuario, LocalDateTime fecha, boolean ok) {}
    model.addAttribute("acciones", List.of(
        new Accion("Alta de socio", "operador1", LocalDateTime.now().minusMinutes(5), true),
        new Accion("Factura generada", "operador2", LocalDateTime.now().minusHours(1), true),
        new Accion("Pago pendiente verificación", "operador1", LocalDateTime.now().minusHours(2), false)
    ));

    return "homeAdmin";
  }
    

}
