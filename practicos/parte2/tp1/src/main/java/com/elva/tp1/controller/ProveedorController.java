package com.elva.tp1.controller;

import com.elva.tp1.domain.Proveedor;
import com.elva.tp1.service.ProveedorService;
import com.elva.tp1.service.DireccionService;
import com.elva.tp1.service.PaisService;
import com.elva.tp1.service.ProvinciaService;
import com.elva.tp1.service.DepartamentoService;
import com.elva.tp1.service.LocalidadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {
    private final ProveedorService proveedorService;
    private final DireccionService direccionService;
    private final PaisService paisService;
    private final ProvinciaService provinciaService;
    private final DepartamentoService departamentoService;
    private final LocalidadService localidadService;

    public ProveedorController(ProveedorService proveedorService, DireccionService direccionService, PaisService paisService,
                               ProvinciaService provinciaService, DepartamentoService departamentoService, LocalidadService localidadService) {
        this.proveedorService = proveedorService;
        this.direccionService = direccionService;
        this.paisService = paisService;
        this.provinciaService = provinciaService;
        this.departamentoService = departamentoService;
        this.localidadService = localidadService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("proveedores", proveedorService.findAll());
        return "proveedor/lista";
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasRole('ADMIN')")
    public String nuevo(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        model.addAttribute("paises", paisService.findAll());
        return "proveedor/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Proveedor proveedor) {
        // Si la dirección no tiene ID, es nueva y se debe persistir automáticamente
        if (proveedor.getDireccion() != null && proveedor.getDireccion().getId() == null) {
            proveedor.getDireccion().setActivo(true); // Establecer activo por defecto
            // Guardar la dirección primero
            proveedor.setDireccion(direccionService.save(proveedor.getDireccion()));
        }
        proveedorService.save(proveedor);
        return "redirect:/proveedores";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Proveedor proveedor = proveedorService.findById(id).orElseThrow();
        model.addAttribute("proveedor", proveedor);
        model.addAttribute("paises", paisService.findAll());

        return "proveedor/form";
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminar(@PathVariable Long id) {
        proveedorService.deleteById(id);
        return "redirect:/proveedores";
    }
}