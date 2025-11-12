package com.example.contactosApp.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.contactosApp.domain.Usuario;
import com.example.contactosApp.domain.dto.UsuarioDTO;
import com.example.contactosApp.service.impl.UsuarioServiceImpl;

import lombok.Getter;

@Controller
@RequestMapping("/usuario")
@Getter
public class UsuarioController extends BaseControllerImpl<Usuario, UsuarioDTO, UsuarioServiceImpl>{
	
	private String viewList = "view/usuario/lUsuario";
	private String viewEdit = "view/usuario/eUsuario";
	private String redirectList= "redirect:/usuario/listar";
	
	@Override
	@GetMapping("/crear")
	public String crear(Model model) {
		
		try {
			
			UsuarioDTO usuario = new UsuarioDTO();
	    	model.addAttribute("isNew", true);
	    	model.addAttribute("usuario", usuario);
	    
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("msgError", "Error de Sistema"); 
	    }
	    
	    return getViewEdit(); 
		
	}

	@GetMapping("detalle/{id}")
	public String detalle(@PathVariable Long id, Model model){
		
		try {
			UsuarioDTO usuario = servicio.findById(id);
			model.addAttribute("usuario", usuario);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msgError", "Error al cargar el detalle del usuario");
		}
		return "view/usuario/detalle";
	}

	
	

}
