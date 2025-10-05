package com.elva.tp1.controller;

import com.elva.tp1.domain.Provincia;
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
@RequestMapping("/provincias")
public class ProvinciaController {
    private final ProvinciaService provinciaService;
    private final PaisService paisService;

    @Autowired
    public ProvinciaController(ProvinciaService provinciaService, PaisService paisService) {
        this.provinciaService = provinciaService;
        this.paisService = paisService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("provincias", provinciaService.findAllByOrderByNombreAsc());
        return "provincia/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("provincia", new Provincia());
        model.addAttribute("paises", paisService.findAllByOrderByNombreAsc());
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
        model.addAttribute("paises", paisService.findAllByOrderByNombreAsc());
        return "provincia/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        provinciaService.softDeleteById(id);
        return "redirect:/provincias";
    }

    // Endpoint REST para obtener provincias por país
    @GetMapping("/api/por-pais/{nombrePais}")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> obtenerProvinciasPorPais(@PathVariable String nombrePais) {
        return ResponseEntity.ok(
                provinciaService.findAllByPais_NombreOrderByNombreAsc(nombrePais).stream()
                        .map(p -> {
                            Map<String, Object> map = new HashMap<>();
                            map.put("id", p.getId());
                            map.put("nombre", p.getNombre());
                            return map;
                        })
                        .toList()
        );
    }
}

