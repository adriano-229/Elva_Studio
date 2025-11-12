package com.projects.mycar.mycar_cliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projects.mycar.mycar_cliente.domain.UsuarioDTO;
import com.projects.mycar.mycar_cliente.service.impl.UsuarioServiceImpl;


import lombok.Getter;


@Controller
@Getter
public class AuthController extends BaseControllerImpl<UsuarioDTO, UsuarioServiceImpl> {

	@Autowired
	private UsuarioServiceImpl service;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/registro")
	public String registro() {
		return "registro";
	}
	
	@Override
	public String crear(Model model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getViewList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getViewEdit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getRedirectList() {
		// TODO Auto-generated method stub
		return null;
	}

}
