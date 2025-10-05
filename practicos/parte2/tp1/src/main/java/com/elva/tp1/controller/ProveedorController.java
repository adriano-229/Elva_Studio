package com.elva.tp1.controller;

import com.elva.tp1.domain.Proveedor;
import com.elva.tp1.service.DireccionService;
import com.elva.tp1.service.ProveedorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {
    private final ProveedorService proveedorService;
    private final DireccionService direccionService;

    public ProveedorController(ProveedorService proveedorService, DireccionService direccionService) {
        this.proveedorService = proveedorService;
        this.direccionService = direccionService;
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
        return "proveedor/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Proveedor proveedor) {
        // Si la dirección no tiene ID, es nueva y se debe persistir automáticamente
        if (proveedor.getDireccion() != null && proveedor.getDireccion().getId() == null) {
            proveedor.getDireccion().setEliminado(true);
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
        return "proveedor/form";
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminar(@PathVariable Long id) {
        proveedorService.softDeleteById(id);
        return "redirect:/proveedores";
    }
}