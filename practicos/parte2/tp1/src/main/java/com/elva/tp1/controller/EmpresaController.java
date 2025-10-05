package com.elva.tp1.controller;

import com.elva.tp1.domain.Empresa;
import com.elva.tp1.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {
    private final EmpresaService empresaService;
    private final DireccionService direccionService;
    private final PaisService paisService;
    private final ProvinciaService provinciaService;
    private final DepartamentoService departamentoService;
    private final LocalidadService localidadService;

    public EmpresaController(EmpresaService empresaService, DireccionService direccionService, PaisService paisService,
                             ProvinciaService provinciaService, DepartamentoService departamentoService, LocalidadService localidadService) {
        this.empresaService = empresaService;
        this.direccionService = direccionService;
        this.paisService = paisService;
        this.provinciaService = provinciaService;
        this.departamentoService = departamentoService;
        this.localidadService = localidadService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("empresas", empresaService.findAll());
        return "empresa/lista";
    }

    @GetMapping("/nueva")
    @PreAuthorize("hasRole('ADMIN')")
    public String nueva(Model model) {
        model.addAttribute("empresa", new Empresa());
        model.addAttribute("paises", paisService.findAll());
        return "empresa/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Empresa empresa) {
        // Si la dirección no tiene ID, es nueva y se debe persistir automáticamente
        if (empresa.getDireccion() != null && empresa.getDireccion().getId() == null) {
            empresa.getDireccion().setActivo(true); // Establecer activo por defecto
            // Guardar la dirección primero
            empresa.setDireccion(direccionService.save(empresa.getDireccion()));
        }
        empresaService.save(empresa);
        return "redirect:/empresas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Empresa empresa = empresaService.findById(id).orElseThrow();
        model.addAttribute("empresa", empresa);
        model.addAttribute("paises", paisService.findAll());
        return "empresa/form";
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminar(@PathVariable Long id) {
        empresaService.deleteById(id);
        return "redirect:/empresas";
    }
}
