package com.projects.gym.gym_app.controller;

import com.projects.gym.gym_app.service.EmpresaService;
import com.projects.gym.gym_app.service.SucursalService;
import com.projects.gym.gym_app.service.dto.SucursalFormDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/sucursales")
public class SucursalController {

    private final SucursalService sucursalService;
    private final EmpresaService empresaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Sucursales");
        model.addAttribute("active", "sucursales");
        model.addAttribute("sucursales", sucursalService.listar());
        return "sucursal/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("titulo", "Nueva sucursal");
        model.addAttribute("active", "sucursales");
        model.addAttribute("form", new SucursalFormDTO());
        model.addAttribute("empresas", empresaService.listar());
        return "sucursal/form";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute("form") SucursalFormDTO form,
                        BindingResult bindingResult,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Nueva sucursal");
            model.addAttribute("active", "sucursales");
            model.addAttribute("empresas", empresaService.listar());
            return "sucursal/form";
        }
        try {
            sucursalService.crear(form);
        } catch (RuntimeException ex) {
            model.addAttribute("titulo", "Nueva sucursal");
            model.addAttribute("active", "sucursales");
            model.addAttribute("empresas", empresaService.listar());
            model.addAttribute("error", ex.getMessage());
            return "sucursal/form";
        }
        redirectAttributes.addFlashAttribute("creado", true);
        return "redirect:/admin/sucursales";
    }
}
