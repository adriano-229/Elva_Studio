package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Base;
import com.example.demo.serviceImp.BaseServiceImp;

public abstract class BaseControllerImp<E extends Base, S extends BaseServiceImp<E,Long>> implements BaseController<E, Long>{
	
	@Autowired
	protected S baseService;
	
	@GetMapping
	@Override
    public String listar(ModelMap model) throws Exception {
        List<E> entidades = baseService.findAll();
        model.addAttribute("entidades", entidades);
        return getListView(); // Template method: definido en subclases
    }
	
	@GetMapping("/nuevo")
    @Override
    public String nuevo(ModelMap model) {
        model.addAttribute("entidad", createNewEntity());
        return getFormView();
    }

	@PostMapping("/guardar")
    @Override
    public String guardar(@ModelAttribute E entity) throws Exception {
        baseService.save(entity);
        return getRedirectToList();
    }
	
	@GetMapping("/editar/{id}")
    @Override
    public String editar(@PathVariable Long id, ModelMap model) throws Exception {
        E entidad = baseService.findByid(id);
        model.addAttribute("entidad", entidad);
        return getFormView();
    }

	@GetMapping("/eliminar/{id}")
    @Override
    public String eliminar(@PathVariable Long id) throws Exception {
        baseService.delete(id);
        return getRedirectToList();
    }
    
    protected abstract String getListView();
    protected abstract String getFormView();
    protected abstract String getRedirectToList();
    protected abstract E createNewEntity();

}
