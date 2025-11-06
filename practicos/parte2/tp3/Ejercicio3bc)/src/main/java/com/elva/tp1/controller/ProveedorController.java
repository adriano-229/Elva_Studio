package com.elva.tp1.controller;

import com.elva.tp1.domain.Departamento;
import com.elva.tp1.domain.Direccion;
import com.elva.tp1.domain.Proveedor;
import com.elva.tp1.service.DepartamentoService;
import com.elva.tp1.service.DireccionService;
import com.elva.tp1.service.PaisService;
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
    private final PaisService paisService;
    private final DepartamentoService departamentoService;

    public ProveedorController(ProveedorService proveedorService,
                               DireccionService direccionService,
                               PaisService paisService,
                               DepartamentoService departamentoService) {
        this.proveedorService = proveedorService;
        this.direccionService = direccionService;
        this.paisService = paisService;
        this.departamentoService = departamentoService;
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
        model.addAttribute("paises", paisService.findAllActivosOrderByNombreAsc());
        return "proveedor/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Proveedor proveedor,
                          @RequestParam(value = "departamentoId", required = false) Long departamentoId) {
        Direccion direccion = proveedor.getDireccion();
        if (direccion != null) {
            if (departamentoId != null) {
                Departamento departamento = departamentoService.findById(departamentoId).orElse(null);
                direccion.setDepartamento(departamento);
            }
            if (direccion.getId() == null) {
                direccion.setEliminado(false);
                proveedor.setDireccion(direccionService.save(direccion));
            }
        }
        proveedorService.save(proveedor);
        return "redirect:/proveedores";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Proveedor proveedor = proveedorService.findById(id).orElseThrow();
        model.addAttribute("proveedor", proveedor);
        model.addAttribute("paises", paisService.findAllActivosOrderByNombreAsc());
        return "proveedor/form";
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminar(@PathVariable Long id) {
        proveedorService.softDeleteById(id);
        return "redirect:/proveedores";
    }

    @GetMapping("/mapa/{id}")
    public String mostrarMapa(@PathVariable Long id) {
        Proveedor proveedor = proveedorService.findById(id).orElseThrow();
        if (proveedor.getDireccion() == null) {
            return "redirect:/proveedores";
        }
        String mapsUrl = direccionService.getGoogleMapsUrl(proveedor.getDireccion());
        return "redirect:" + mapsUrl;
    }
}