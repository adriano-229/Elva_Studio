package com.example.biblioteca.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.biblioteca.domain.dto.DomicilioDTO;
import com.example.biblioteca.domain.dto.LocalidadDTO;
import com.example.biblioteca.service.impl.DomicilioServiceImpl;

import lombok.Getter;

@Controller
@RequestMapping("/domicilio")
@Getter
public class DomicilioController extends BaseControllerImpl<DomicilioDTO, DomicilioServiceImpl>{
	
	/*public DomicilioController() {
		super(DomicilioDTO.class);
	}*/

	private String viewList = "view/domicilio/listar";
	private String viewEdit = "view/domicilio/eDomicilios";
	private String redirectList= "redirect:/domicilio/listar";
	
	@GetMapping("/crear")
	public String crear(Model model){
	    try {
	    	DomicilioDTO domicilio = new DomicilioDTO();
	    	domicilio.setLocalidad(new LocalidadDTO());
	    	model.addAttribute("isNew", true);
	    	model.addAttribute("domicilio", domicilio);
	    
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("msgError", "Error de Sistema"); 
	    }
	    
	    return getViewEdit(); 
	}
	

	// ARREGLAR PQ ESTO LO USO SOLO CUANDO QUEIRO AGREGAR UN NUEVO DOMICILIO
	@GetMapping("/search")
	public String searchByCalleNumeroYLocalidad(@RequestParam String calle, @RequestParam int numero, @RequestParam String denominacion, Model model) {
		
		try {
			
			DomicilioDTO domicilio = servicio.buscarPorCalleNroYLocalidad(calle, numero, denominacion);
			
			if (domicilio == null) {
				model.addAttribute("msgError", "El domicilio no se encuentra registrado"); 
				 model.addAttribute("domicilio", new DomicilioDTO()); // evita null en la vista
			}
			
			model.addAttribute("domicilio", domicilio);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msgError", "Error de Sistema"); 

	        model.addAttribute("domicilio", new DomicilioDTO()); // previene NPE en la vista
		}
		
		return "colocar el path";
		
	}
	
	

}
