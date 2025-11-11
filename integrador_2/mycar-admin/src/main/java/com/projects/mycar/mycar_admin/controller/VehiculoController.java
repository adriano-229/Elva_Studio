package com.projects.mycar.mycar_admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.mycar.mycar_admin.domain.VehiculoDTO;
import com.projects.mycar.mycar_admin.service.impl.VehiculoServiceImpl;

import lombok.Getter;

@Controller
@RequestMapping("/vehiculo")
@Getter
public class VehiculoController extends BaseControllerImpl<VehiculoDTO, VehiculoServiceImpl>{
	
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
