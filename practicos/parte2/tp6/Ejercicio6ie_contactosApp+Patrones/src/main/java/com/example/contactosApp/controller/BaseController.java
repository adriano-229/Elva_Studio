package com.example.contactosApp.controller;

import java.io.Serializable;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.contactosApp.domain.dto.BaseDTO;


public interface BaseController <D extends BaseDTO, ID extends Serializable>{
	
	public String listar(Model model);
	public String crear(Model model);
	public String guardar(D entity, Model model);
	public String modificar(ID id, Model model);
	public String detalle(ID id, Model model);
	public String delete(ID id, RedirectAttributes redirectAttributes);
}
