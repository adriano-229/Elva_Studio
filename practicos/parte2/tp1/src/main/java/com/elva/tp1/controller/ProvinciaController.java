package com.elva.tp1.controller;

import com.elva.tp1.domain.Provincia;
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
@RequestMapping("/provincias")
public class ProvinciaController {
    private final ProvinciaService provinciaService;
    private final PaisService paisService;

    public ProvinciaController(ProvinciaService provinciaService, PaisService paisService) {
        this.provinciaService = provinciaService;
        this.paisService = paisService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("provincias", provinciaService.findAll());
        return "provincia/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("provincia", new Provincia());
        model.addAttribute("paises", paisService.findAll());
        return "provincia/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Provincia provincia) {
        provinciaService.save(provincia);
        return "redirect:/provincias";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("provincia", provinciaService.findById(id).orElseThrow());
        model.addAttribute("paises", paisService.findAll());
        return "provincia/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        provinciaService.deleteById(id);
        return "redirect:/provincias";
    }

    // Endpoint REST para obtener provincias por país
    @GetMapping("/api/por-pais/{paisId}")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> obtenerProvinciasPorPais(@PathVariable Long paisId) {
        List<Provincia> provincias = provinciaService.findByPaisId(paisId);

        // Convertir a Map simple para evitar problemas de serialización
        List<Map<String, Object>> result = provincias.stream().map(provincia -> {
            Map<String, Object> provinciaMap = new HashMap<>();
            provinciaMap.put("id", provincia.getId());
            provinciaMap.put("nombre", provincia.getNombre());
            return provinciaMap;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}
