package com.projects.mycar.mycar_admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.mycar.mycar_admin.domain.UsuarioDTO;
import com.projects.mycar.mycar_admin.service.impl.UsuarioServiceImpl;

import lombok.Getter;

@Controller
@RequestMapping("/usuario")
@Getter
public class UsuarioController extends BaseControllerImpl<UsuarioDTO, UsuarioServiceImpl> {

	private String viewList = "";
	private String viewEdit = "";
	private String redirectList= "";
	
	@Override
	@GetMapping("/crear")
	public String crear(Model model) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
