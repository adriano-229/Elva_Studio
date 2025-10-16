package com.example.proyectocarrito.controller;

import com.example.proyectocarrito.domain.Proveedor;
import com.example.proyectocarrito.domain.Carrito;
import com.example.proyectocarrito.repository.CarritoRepo;
import com.example.proyectocarrito.repository.ProveedorRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;

// ProveedorController.java
@Controller
public class ProveedorController {

    private final ProveedorRepo proveedorRepo;
    private final CarritoRepo carritoRepo;

    public ProveedorController(ProveedorRepo repo, CarritoRepo carritoRepo){
        this.proveedorRepo = repo;
        this.carritoRepo = carritoRepo;
    }

    @ModelAttribute("carritoTotal")
    public double carritoTotal(HttpSession session) {
        String carritoId = (String) session.getAttribute("carritoId");
        if (carritoId == null) return 0d;
        return carritoRepo.findById(carritoId).map(Carrito::getTotal).orElse(0d);
    }
    
    // Página con el mapa
    @GetMapping("/proveedores/mapa")
    public String verMapa(Model model){
        model.addAttribute("proveedores", proveedorRepo.findAll());
        return "proveedores_mapa";
    }

    // (Opcional) Form simple para crear/editar proveedor
    @GetMapping("/proveedores/nuevo")
    public String formNuevo(Model model){
        model.addAttribute("proveedor", new Proveedor());
        return "proveedor_form";
    }

    @PostMapping("/proveedores")
    public String guardar(@ModelAttribute Proveedor p){
        proveedorRepo.save(p);
        return "redirect:/proveedores/mapa";
    }
}
