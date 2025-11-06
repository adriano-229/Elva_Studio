package com.projects.gym.gym_app.controller;

import com.projects.gym.gym_app.service.EmpresaService;
import com.projects.gym.gym_app.service.dto.EmpresaFormDTO;
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
@RequestMapping("/admin/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("titulo", "Empresas");
        model.addAttribute("active", "empresas");
        model.addAttribute("empresas", empresaService.listar());
        return "empresa/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("titulo", "Nueva empresa");
        model.addAttribute("active", "empresas");
        model.addAttribute("form", new EmpresaFormDTO());
        return "empresa/form";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute("form") EmpresaFormDTO form,
                        BindingResult bindingResult,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Nueva empresa");
            model.addAttribute("active", "empresas");
            return "empresa/form";
        }
        empresaService.crear(form);
        redirectAttributes.addFlashAttribute("creado", true);
        return "redirect:/admin/empresas";
    }
}
