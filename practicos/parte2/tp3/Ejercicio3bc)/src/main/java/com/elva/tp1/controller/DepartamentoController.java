package com.elva.tp1.controller;

import com.elva.tp1.domain.Departamento;
import com.elva.tp1.service.DepartamentoService;
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
@RequestMapping("/departamentos")
public class DepartamentoController {
    private final DepartamentoService departamentoService;
    private final ProvinciaService provinciaService;
    private final PaisService paisService;

    @Autowired
    public DepartamentoController(DepartamentoService departamentoService, ProvinciaService provinciaService, PaisService paisService) {
        this.departamentoService = departamentoService;
        this.provinciaService = provinciaService;
        this.paisService = paisService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("departamentos", departamentoService.findAllByOrderByNombreAsc());
        return "departamento/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("departamento", new Departamento());
        model.addAttribute("provincias", provinciaService.findAllActivasOrderByNombreAsc());
        model.addAttribute("paises", paisService.findAllActivosOrderByNombreAsc());
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
        model.addAttribute("provincias", provinciaService.findAllActivasOrderByNombreAsc());
        model.addAttribute("paises", paisService.findAllActivosOrderByNombreAsc());
        return "departamento/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        departamentoService.softDeleteById(id);
        return "redirect:/departamentos";
    }

    // Endpoint REST original por nombre (compatibilidad) filtrando activos
    @GetMapping("/api/por-provincia/nombre/{nombreProvincia}")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> obtenerDepartamentosPorProvinciaNombre(@PathVariable String nombreProvincia) {
        return ResponseEntity.ok(
                departamentoService.findAllActivosByProvinciaNombreOrderByNombre(nombreProvincia).stream()
                        .map(p -> {
                            Map<String, Object> map = new HashMap<>();
                            map.put("id", p.getId());
                            map.put("nombre", p.getNombre());
                            return map;
                        })
                        .toList()
        );
    }

    // Nuevo endpoint REST por ID de provincia filtrando activos
    @GetMapping("/api/por-provincia/{provinciaId}")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> obtenerDepartamentosPorProvinciaId(@PathVariable Long provinciaId) {
        return ResponseEntity.ok(
                departamentoService.findAllActivosByProvinciaIdOrderByNombre(provinciaId).stream()
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
