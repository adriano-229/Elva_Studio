package com.projects.gym.gym_app.controller;

import com.projects.gym.gym_app.service.*;
import com.projects.gym.gym_app.service.dto.DireccionDTO;
import com.projects.gym.gym_app.service.query.UbicacionQueryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/direcciones")
public class DireccionController {

    private final DireccionService direccionService;
    private final UbicacionQueryService ubicacionQueryService;

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("form", new DireccionDTO());
        model.addAttribute("paises", ubicacionQueryService.listarPaisesActivos());
        return "direccion/form";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute("form") DireccionDTO form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("paises", ubicacionQueryService.listarPaisesActivos());
            return "direccion/form";
        }
        direccionService.crear(form);
        return "redirect:/admin/direcciones";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable String id, Model model) {
        model.addAttribute("form", direccionService.buscarPorId(id));
        model.addAttribute("paises", ubicacionQueryService.listarPaisesActivos());
        return "direccion/form";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable String id,
                             @Valid @ModelAttribute("form") DireccionDTO form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("paises", ubicacionQueryService.listarPaisesActivos());
            return "direccion/form";
        }
        direccionService.modificar(id, form);
        return "redirect:/admin/direcciones";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable String id) {
        direccionService.eliminarLogico(id);
        return "redirect:/admin/direcciones";
    }
}
