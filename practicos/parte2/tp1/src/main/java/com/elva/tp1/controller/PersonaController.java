package com.elva.tp1.controller;

import com.elva.tp1.domain.Persona;
import com.elva.tp1.service.PersonaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/personas")
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("personas", personaService.findAll());
        return "persona/lista"; // templates/persona/lista.html
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("persona", new Persona());
        return "persona/form"; // templates/persona/form.html
    }

    @PostMapping
    public String guardar(@ModelAttribute Persona persona) {
        personaService.save(persona);
        return "redirect:/personas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("persona", personaService.findById(id).orElseThrow());
        return "persona/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        personaService.deleteById(id);
        return "redirect:/personas";
    }
}
