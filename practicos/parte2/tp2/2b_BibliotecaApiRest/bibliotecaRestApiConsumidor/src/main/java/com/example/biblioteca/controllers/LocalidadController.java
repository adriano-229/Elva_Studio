package com.example.biblioteca.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.biblioteca.domain.dto.LocalidadDTO;
import com.example.biblioteca.service.impl.LocalidadServiceImpl;

import lombok.Getter;

@Controller
@RequestMapping("/localidad")
@Getter
public class LocalidadController extends BaseControllerImpl<LocalidadDTO, LocalidadServiceImpl>{
	
	/*public LocalidadController() {
		super(LocalidadDTO.class);
	}*/

	private String viewList = "view/localidad/lLocalidad";
	private String viewEdit = "view/localidad/eLocalidad";
	private String redirectList= "redirect:/localidad/listar";
	
	@GetMapping("/crear")
	public String crear(Model model){
	    try {
	    	LocalidadDTO localidad = new LocalidadDTO();
	    	model.addAttribute("isNew", true);
	    	model.addAttribute("entity", localidad);
	    
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("msgError", "Error de Sistema"); 
	    }
	    
	    return getViewEdit(); 
	}
	
	@GetMapping("/searchByDenominacion")
	public String searchByDenominacion(@RequestParam String denominacion, Model model) {
		
		try {
			
			List<LocalidadDTO> lista = servicio.searchByDenominacion(denominacion);
			
			if (lista == null || lista.isEmpty()) {
	            model.addAttribute("msgError", "No se encontró la localidad");
	            lista = new ArrayList<LocalidadDTO>(); // para que la tabla no explote
	        }

	        model.addAttribute("lista", lista);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msgError", "Error al realizar la búsqueda");
		    model.addAttribute("lista", new ArrayList<>());
		}
		
		return viewList;
	}

}
