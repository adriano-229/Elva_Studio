package com.example.demo.controllers;

import java.io.Serializable;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entities.Base;

public interface BaseController<E extends Base, ID extends Serializable> {
	
	String listar(ModelMap model) throws Exception;
	String nuevo(ModelMap model);
	String guardar(@ModelAttribute E entity) throws Exception;
	String editar(@PathVariable ID id, ModelMap model) throws Exception;
	String eliminar(@PathVariable ID id) throws Exception;
}
