package com.elva.tp1.controller;

import com.elva.tp1.domain.Empresa;
import com.elva.tp1.domain.Departamento;
import com.elva.tp1.service.DireccionService;
import com.elva.tp1.service.EmpresaService;
import com.elva.tp1.service.PaisService;
import com.elva.tp1.service.DepartamentoService;
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
    private final DepartamentoService departamentoService;

    public EmpresaController(EmpresaService empresaService,
                             DireccionService direccionService,
                             PaisService paisService,
                             DepartamentoService departamentoService) {
        this.empresaService = empresaService;
        this.direccionService = direccionService;
        this.paisService = paisService;
        this.departamentoService = departamentoService;
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
        model.addAttribute("paises", paisService.findAllActivosOrderByNombreAsc());
        return "empresa/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Empresa empresa,
                          @RequestParam(value = "departamentoId", required = false) Long departamentoId) {
        if (empresa.getDireccion() != null) {
            if (departamentoId != null) {
                Departamento dep = departamentoService.findById(departamentoId).orElse(null);
                if (dep != null) {
                    empresa.getDireccion().setDepartamento(dep);
                }
            }
            if (empresa.getDireccion().getId() == null) {
                empresa.getDireccion().setEliminado(false);
                empresa.setDireccion(direccionService.save(empresa.getDireccion()));
            }
        }
        empresaService.save(empresa);
        return "redirect:/empresas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Empresa empresa = empresaService.findById(id).orElseThrow();
        model.addAttribute("empresa", empresa);
        model.addAttribute("paises", paisService.findAllActivosOrderByNombreAsc());
        return "empresa/form";
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminar(@PathVariable Long id) {
        empresaService.softDeleteById(id);
        return "redirect:/empresas";
    }
}
