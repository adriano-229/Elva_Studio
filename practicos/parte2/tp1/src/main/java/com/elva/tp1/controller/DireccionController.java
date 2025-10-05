package com.elva.tp1.controller;

import com.elva.tp1.domain.Departamento;
import com.elva.tp1.domain.Direccion;
import com.elva.tp1.service.DepartamentoService;
import com.elva.tp1.service.DireccionService;
import com.elva.tp1.service.PaisService;
import com.elva.tp1.service.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/direcciones")
public class DireccionController {
    private final DireccionService direccionService;
    private final DepartamentoService departamentoService;
    private final ProvinciaService provinciaService;
    private final PaisService paisService;

    @Autowired
    public DireccionController(DireccionService direccionService, DepartamentoService departamentoService, ProvinciaService provinciaService, PaisService paisService) {
        this.direccionService = direccionService;
        this.departamentoService = departamentoService;
        this.provinciaService = provinciaService;
        this.paisService = paisService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("direcciones", direccionService.findAllByOrderByCalleAsc());
        return "departamento/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("direccion", new Direccion());
        model.addAttribute("departamentos", departamentoService.findAllByOrderByNombreAsc());
        model.addAttribute("provincias", provinciaService.findAllByOrderByNombreAsc());
        model.addAttribute("paises", paisService.findAllByOrderByNombreAsc());
        return "departamento/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Departamento departamento) {
        departamentoService.save(departamento);
        return "redirect:/direcciones";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("direccion", direccionService.findById(id).orElseThrow());
        model.addAttribute("departamentos", departamentoService.findAllByOrderByNombreAsc());
        model.addAttribute("provincias", provinciaService.findAllByOrderByNombreAsc());
        model.addAttribute("paises", paisService.findAllByOrderByNombreAsc());
        return "departamento/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        departamentoService.softDeleteById(id);
        return "redirect:/direcciones";
    }

    // Endpoint REST para obtener direcciones por departamento
    @GetMapping("/api/por-departamento/{nombreDepartamento}")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> obtenerDireccionesPorProvincia(@PathVariable String nombreDepartamento) {
        return ResponseEntity.ok(
                direccionService.findAllByDepartamento_NombreOrderByCalleAsc(nombreDepartamento).stream()
                        .map(p -> {
                            Map<String, Object> map = new HashMap<>();
                            map.put("id", p.getId());
                            map.put("calle", p.getCalle());
                            map.put("altura", p.getAltura());
                            return map;
                        })
                        .toList()
        );
    }
}
