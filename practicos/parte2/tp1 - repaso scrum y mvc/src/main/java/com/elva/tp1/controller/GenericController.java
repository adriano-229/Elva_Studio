package com.elva.tp1.controller;

import com.elva.tp1.entity.Activable;
import com.elva.tp1.service.GenericService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public abstract class GenericController<T extends Activable, ID> {

    protected final GenericService<T, ID> service;
    protected final String basePath; // ej "/pais"
    protected final String viewFolder; // ej "pais"

    protected GenericController(GenericService<T, ID> service, String basePath, String viewFolder) {
        this.service = service;
        this.basePath = basePath;
        this.viewFolder = viewFolder;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("items", service.findAllActive()); // ahora solo activos
        return viewFolder + "/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        try {
            Class<?> clazz = Class.forName(getEntityClassName());
            Object obj = clazz.getDeclaredConstructor().newInstance();
            model.addAttribute("item", obj);
        } catch (Exception e) {
            model.addAttribute("item", null);
        }
        return viewFolder + "/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("item") T item) {
        service.save(item);
        return "redirect:" + basePath;
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable ID id) {
        service.delete(id); // lógico
        return "redirect:" + basePath;
    }

    protected abstract String getEntityClassName();
}
