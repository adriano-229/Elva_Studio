package com.elva.tp1.controller;

import com.elva.tp1.entity.Provincia;
import com.elva.tp1.service.PaisService;
import com.elva.tp1.service.ProvinciaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/provincia")
public class ProvinciaController {

    private final ProvinciaService provinciaService;
    private final PaisService paisService;

    public ProvinciaController(ProvinciaService provinciaService, PaisService paisService) {
        this.provinciaService = provinciaService;
        this.paisService = paisService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("provincias", provinciaService.findAllActive());
        return "provincia/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("provincia", new Provincia());
        model.addAttribute("paises", paisService.findAllActive());
        return "provincia/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Provincia provincia) {
        provinciaService.save(provincia);
        return "redirect:/provincia";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        provinciaService.delete(id); // borrado lógico (set activo=false)
        return "redirect:/provincia";
    }
}
