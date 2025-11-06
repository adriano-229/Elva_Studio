package com.projects.gym.gym_app.controller;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projects.gym.gym_app.domain.Factura;
import com.projects.gym.gym_app.domain.Socio;
import com.projects.gym.gym_app.domain.Usuario;
import com.projects.gym.gym_app.repository.SocioRepository;
import com.projects.gym.gym_app.service.FacturaService;
import com.projects.gym.gym_app.service.impl.FacturaServiceImpl;

@Controller
@RequestMapping("/socio/facturas")
public class FacturaSocioController {
	
	@Autowired
	private FacturaServiceImpl facturaService;
	
	@Autowired
	private SocioRepository socioRepo;
	
	@GetMapping
	public String listar(Authentication authentication, Model model) throws Exception {
		
		if (authentication == null || !authentication.isAuthenticated()) {
	        return "redirect:/login";
	    }
		
		try {
	        Usuario usuario = (Usuario) authentication.getDetails();

	        Socio socio = this.socioRepo
	                         .findByUsuario_NombreUsuarioAndEliminadoFalse(authentication.getName())
	                         .orElseThrow(() -> new RuntimeException("El usuario no tiene un socio asociado"));
	        
	        System.out.println("SOCIO ID: " + socio.getId());

	        model.addAttribute("socio", socio);

	        List<Factura> facturas = facturaService.listarPagadas(socio.getId());
		    
	        System.out.println("EN EL CONTROLADOR, TAMAÑO DE FACTURAS: " + facturas.size());
	        
	        model.addAttribute("listaFacturas", facturas);
		    model.addAttribute("socioId", socio.getId());
		    

	    } catch (Exception e) {
	        model.addAttribute("msgError", "Error de Sistema: " + e.getMessage());
	        
	    }
		
		return "socio/facturas";
	}
}
