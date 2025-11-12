package com.projects.mycar.mycar_admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.mycar.mycar_admin.domain.AlquilerDTO;
import com.projects.mycar.mycar_admin.service.impl.AlquilerServiceImpl;

import lombok.Getter;

@Controller
@RequestMapping("/alquiler")
@Getter
public class AlquilerController extends BaseControllerImpl<AlquilerDTO, AlquilerServiceImpl>{
	
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
