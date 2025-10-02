package com.elva.tp1.controller;

import com.elva.tp1.domain.Localidad;
import com.elva.tp1.service.LocalidadService;
import com.elva.tp1.service.DepartamentoService;
import com.elva.tp1.service.PaisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/localidades")
public class LocalidadController {
    private final LocalidadService localidadService;
    private final DepartamentoService departamentoService;
    private final PaisService paisService;

    public LocalidadController(LocalidadService localidadService, DepartamentoService departamentoService, PaisService paisService) {
        this.localidadService = localidadService;
        this.departamentoService = departamentoService;
        this.paisService = paisService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("localidades", localidadService.findAll());
        return "localidad/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("localidad", new Localidad());
        model.addAttribute("paises", paisService.findAll());
        return "localidad/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Localidad localidad) {
        localidadService.save(localidad);
        return "redirect:/localidades";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("localidad", localidadService.findById(id).orElseThrow());
        model.addAttribute("paises", paisService.findAll());
        return "localidad/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        localidadService.deleteById(id);
        return "redirect:/localidades";
    }

    // Endpoint REST para obtener localidades por departamento
    @GetMapping("/api/por-departamento/{departamentoId}")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> obtenerLocalidadesPorDepartamento(@PathVariable Long departamentoId) {
        List<Localidad> localidades = localidadService.findByDepartamentoId(departamentoId);

        // Convertir a Map simple para evitar problemas de serialización
        List<Map<String, Object>> result = localidades.stream().map(localidad -> {
            Map<String, Object> localidadMap = new java.util.HashMap<>();
            localidadMap.put("id", localidad.getId());
            localidadMap.put("nombre", localidad.getNombre());
            return localidadMap;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}
