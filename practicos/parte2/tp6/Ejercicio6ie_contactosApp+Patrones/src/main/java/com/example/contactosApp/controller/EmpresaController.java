package com.example.contactosApp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.contactosApp.domain.Empresa;
import com.example.contactosApp.domain.dto.ContactoDTO;
import com.example.contactosApp.domain.dto.EmpresaDTO;
import com.example.contactosApp.service.ContactoService;
import com.example.contactosApp.service.impl.ContactoServiceImpl;
import com.example.contactosApp.service.impl.EmpresaServiceImpl;

import lombok.Getter;

@Controller
@Getter
@RequestMapping("/empresa")
public class EmpresaController extends BaseControllerImpl<Empresa, EmpresaDTO, EmpresaServiceImpl>{
	
	@Autowired
    private List<ContactoServiceImpl<?, ?>> contactoServices;
	
	private String viewList = "view/empresa/lEmpresa";
	private String viewEdit = "view/empresa/eEmpresa";
	private String redirectList= "redirect:/empresa/listar";
	
	@Override
	@GetMapping("/crear")
	public String crear(Model model) {
		
		try {
			// controlar que al crear un contacto solo puede tener un contacto activo
			EmpresaDTO empresa = new EmpresaDTO();
			model.addAttribute("isNew", true);
	    	model.addAttribute("entity", empresa);
	    	
	    	
	   } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("msgError", "Error de Sistema"); 
	   }
	    
	    return getViewEdit(); 
		
	}
		
	
	
	@Override
	@GetMapping("/detalle/{id}")
	public String detalle(@PathVariable Long id, Model model) {
		
		// No se su funciona, voy a suponer que el mapper me va a mapear tambien los objetos
		try {
			EmpresaDTO empresa = servicio.findById(id);
			
			System.out.println("CONTROLADOR");
			System.out.println("EMPRESA: " + empresa);
			
			model.addAttribute("empresa", empresa);
		
			for (ContactoService<?> servicio : contactoServices) {
	           ContactoDTO contacto = servicio.findByEmpresa(id);
	           if (contacto != null) {
	        	   contacto.setClase(contacto.getClass().getSimpleName());
		           model.addAttribute("contacto", contacto);
		           break;
	           }
	           
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msgError", "Error al cargar el detalle de la empresa");
		}
		
		return "view/empresa/detalle";
		
	}
		
}
