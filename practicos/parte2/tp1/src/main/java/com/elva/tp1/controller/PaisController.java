package com.elva.tp1.controller;

import com.elva.tp1.domain.Pais;
import com.elva.tp1.service.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/paises")

public class PaisController {
    private final PaisService paisService;

    @Autowired
    public PaisController(PaisService paisService) {
        this.paisService = paisService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("paises", paisService.findAll());
        return "pais/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("pais", new Pais());
        return "pais/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Pais pais) {
        paisService.save(pais);
        return "redirect:/paises";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("pais", paisService.findById(id).orElseThrow());
        return "pais/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        paisService.softDeleteById(id);
        return "redirect:/paises";
    }
}
