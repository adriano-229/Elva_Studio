package com.example.contactosApp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.contactosApp.domain.Usuario;
import com.example.contactosApp.domain.dto.ContactoDTO;
import com.example.contactosApp.domain.dto.EmpresaDTO;
import com.example.contactosApp.domain.dto.UsuarioDTO;
import com.example.contactosApp.service.ContactoService;
import com.example.contactosApp.service.impl.EmpresaServiceImpl;
import com.example.contactosApp.service.impl.UsuarioServiceImpl;

@Controller
@RequestMapping("/home")
public class RolesController {
	
	@Autowired
	private EmpresaServiceImpl empresaService;
	
	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@Autowired
	private List<ContactoService<?>> contactoServices;
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String accessAdmin(Authentication authentication, Model model) throws Exception {
		
		if (authentication == null || !authentication.isAuthenticated()) {
	        return "redirect:/login";
	    }
		
		String username = authentication.getName(); // ← usuario logueado
	    System.out.println("USERNAME: " + username);
	    UsuarioDTO usuarioDTO = usuarioService.findByCuenta(username);
	    System.out.println("persona: " + usuarioDTO.getPersona());
	    
		List<EmpresaDTO> empresas = empresaService.findAll();
		model.addAttribute("empresas", empresas);
		model.addAttribute("usuario", usuarioDTO);
		model.addAttribute("persona", usuarioDTO.getPersona());
		return "view/usuario/homeAdmin";
	}
	
	@GetMapping("/user")		//@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@PreAuthorize("hasRole('USER')") //@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public String accessUser(Authentication authentication, Model model) throws Exception {
		
		if (authentication == null || !authentication.isAuthenticated()) {
	        return "redirect:/login";
	    }
		
		Usuario usuario = (Usuario) authentication.getDetails();
		
		try {
			//String username = authentication.getName(); // ← usuario logueado
		    
		    UsuarioDTO usuarioDTO = usuarioService.findByCuenta(usuario.getCuenta());
		    List<ContactoDTO> contactos = new ArrayList<>();
			for (ContactoService<?> servicio : contactoServices) {
			    contactos.addAll(servicio.findByPersona(usuarioDTO.getPersona().getId()));
			}
		    
		    model.addAttribute("contacos", contactos);
		    model.addAttribute("usuario", usuarioDTO);
			model.addAttribute("persona", usuarioDTO.getPersona());
			
			
		    
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msgError", "Error al listar contactos");
		}
		
		return "redirect:/persona/detalle/" + usuario.getPersona().getId();
	}

}
