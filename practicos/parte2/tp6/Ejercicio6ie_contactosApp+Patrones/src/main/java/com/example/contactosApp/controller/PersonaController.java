package com.example.contactosApp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.contactosApp.domain.Contacto;
import com.example.contactosApp.domain.Persona;
import com.example.contactosApp.domain.Usuario;
import com.example.contactosApp.domain.dto.ContactoDTO;
import com.example.contactosApp.domain.dto.PersonaDTO;
import com.example.contactosApp.domain.dto.UsuarioDTO;
import com.example.contactosApp.service.ContactoService;
import com.example.contactosApp.service.impl.ContactoServiceImpl;
import com.example.contactosApp.service.impl.PersonaServiceImpl;
import com.example.contactosApp.service.impl.UsuarioServiceImpl;

import lombok.Getter;

@Controller
@Getter
@RequestMapping("/persona")
public class PersonaController extends BaseControllerImpl<Persona, PersonaDTO, PersonaServiceImpl> {

	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@Autowired
	private List<ContactoService<?>> contactoServices;
	
	private String viewList = "view/persona/lPersona";
	private String viewEdit = "view/persona/ePersona";
	private String redirectList;
	
	@Override
	@GetMapping("/crear")
	public String crear(Model model) {
		
		try {
			
			PersonaDTO persona = new PersonaDTO();
			UsuarioDTO usuario = new UsuarioDTO();
			usuario.setPersona(persona);
	    	model.addAttribute("isNew", true);
	    	model.addAttribute("entity", persona);
	    	model.addAttribute("usuario", usuario);
	    
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
		System.out.println("ESTOY EN PERSONA DETALLE");
		try {
			
			PersonaDTO persona = servicio.findById(id);
			UsuarioDTO usuario = usuarioService.findByPersona(persona.getId());
			List<ContactoDTO> contactos = new ArrayList<>();
			for (ContactoService<?> servicio : contactoServices) {
	            List<? extends ContactoDTO> lista = servicio.findByPersona(persona.getId());
	            System.out.println("BUSCANDO LISTA DE CONTACTOS");
	            // Agregar el nombre de la clase al atributo 'clase'
	            for (ContactoDTO contacto : lista) {
	            	
	            	contacto.setClase(contacto.getClass().getSimpleName());
	            }

	            contactos.addAll(lista);
	        }
			usuario.setPersona(persona);
			model.addAttribute("listaContactos", contactos);
			model.addAttribute("persona", persona);
			model.addAttribute("usuario", usuario);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msgError", "Error al cargar el detalle de persona");
		}
		
		return "view/usuario/detalle";
		
	}
	
}
