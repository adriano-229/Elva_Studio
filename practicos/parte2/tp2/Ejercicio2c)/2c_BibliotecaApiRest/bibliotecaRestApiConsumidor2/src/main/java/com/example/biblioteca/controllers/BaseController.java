package com.example.biblioteca.controllers;

import java.io.Serializable;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.biblioteca.domain.dto.BaseDTO;

public interface BaseController <E extends BaseDTO, ID extends Serializable>{
	
	public String listar(Model model);
	public String crear(Model model);
	public String guardar(E entity, Model model, RedirectAttributes redirectAttributes);
	public String modificar(ID id, Model model);
	public String delete(ID id, RedirectAttributes redirectAttributes);
}
