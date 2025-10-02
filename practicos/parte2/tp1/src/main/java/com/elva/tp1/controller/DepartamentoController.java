package com.elva.tp1.controller;

import com.elva.tp1.domain.Departamento;
import com.elva.tp1.service.DepartamentoService;
import com.elva.tp1.service.ProvinciaService;
import com.elva.tp1.service.PaisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {
    private final DepartamentoService departamentoService;
    private final ProvinciaService provinciaService;
    private final PaisService paisService;

    public DepartamentoController(DepartamentoService departamentoService, ProvinciaService provinciaService, PaisService paisService) {
        this.departamentoService = departamentoService;
        this.provinciaService = provinciaService;
        this.paisService = paisService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("departamentos", departamentoService.findAll());
        return "departamento/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("departamento", new Departamento());
        model.addAttribute("paises", paisService.findAll());
        return "departamento/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Departamento departamento) {
        departamentoService.save(departamento);
        return "redirect:/departamentos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("departamento", departamentoService.findById(id).orElseThrow());
        model.addAttribute("paises", paisService.findAll());
        return "departamento/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        departamentoService.deleteById(id);
        return "redirect:/departamentos";
    }

    // Endpoint REST para obtener departamentos por provincia
    @GetMapping("/api/por-provincia/{provinciaId}")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> obtenerDepartamentosPorProvincia(@PathVariable Long provinciaId) {
        List<Departamento> departamentos = departamentoService.findByProvinciaId(provinciaId);

        // Convertir a Map simple para evitar problemas de serialización
        List<Map<String, Object>> result = departamentos.stream().map(departamento -> {
            Map<String, Object> departamentoMap = new HashMap<>();
            departamentoMap.put("id", departamento.getId());
            departamentoMap.put("nombre", departamento.getNombre());
            return departamentoMap;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}
