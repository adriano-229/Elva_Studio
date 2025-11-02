package com.projects.gym.gym_app.controller;

import com.projects.gym.gym_app.domain.CuotaMensual;
import com.projects.gym.gym_app.domain.DetalleDiario;
import com.projects.gym.gym_app.domain.Rutina;
import com.projects.gym.gym_app.domain.Socio;
import com.projects.gym.gym_app.domain.Usuario;
import com.projects.gym.gym_app.domain.enums.EstadoCuota;
import com.projects.gym.gym_app.repository.SocioRepository;
import com.projects.gym.gym_app.service.CuotaMensualService;
import com.projects.gym.gym_app.service.RutinaService;
import com.projects.gym.gym_app.service.dto.DeudaForm;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/socio")
@RequiredArgsConstructor
public class SocioPortalController {

    private final RutinaService rutinaService;
    private final SocioRepository socioRepository;
        
    @Autowired
    private SocioRepository repoSocio;
    
    @Autowired
    private CuotaMensualService svcCuota;

    @GetMapping("/portal")
    public String portal(Authentication authentication, Model model) {
        String username = authentication != null ? authentication.getName() : "socio";
        model.addAttribute("titulo", "Portal de Socios");
        model.addAttribute("seccion", "Socios");
        model.addAttribute("usuarioActual", username);
        model.addAttribute("active", "socio-portal");
        return "socio/portal";
       
    }

    @GetMapping("/rutinas")
    public String rutinas(Authentication authentication, Model model) {
        String username = authentication != null ? authentication.getName() : null;
        model.addAttribute("titulo", "Mis rutinas");
        model.addAttribute("seccion", "Socios");
        model.addAttribute("usuarioActual", username);
        model.addAttribute("active", "socio-rutinas");

        if (username == null) {
            model.addAttribute("rutinaNoEncontrada", true);
            return "socio/rutinas";
        }

        Socio socio = socioRepository.findByUsuario_NombreUsuarioAndEliminadoFalse(username)
                .orElse(null);
        if (socio == null) {
            model.addAttribute("rutinaNoEncontrada", true);
            return "socio/rutinas";
        }

        Rutina rutina = rutinaService.buscarRutinaActual(socio.getNumeroSocio());
        List<Rutina> rutinasFinalizadas = rutinaService.listarRutinasFinalizadasPorSocio(socio.getNumeroSocio());
        model.addAttribute("rutinasFinalizadas", rutinasFinalizadas);
        if (rutina == null) {
            model.addAttribute("rutinaNoEncontrada", true);
            return "socio/rutinas";
        }

        List<DetalleDiario> detalles = rutina.getDetallesDiarios() != null
                ? rutina.getDetallesDiarios().stream()
                .sorted(Comparator.comparingInt(d -> d.getDiaSemana() != null ? d.getDiaSemana().ordinal() : Integer.MAX_VALUE))
                .toList()
                : List.of();

        model.addAttribute("rutina", rutina);
        model.addAttribute("detalles", detalles);
        return "socio/rutinas";
    }

    @GetMapping("/deuda")
    public String deuda(Authentication authentication, Model model) {
    	
    	if (authentication == null || !authentication.isAuthenticated()) {
	        return "redirect:/login";
	    }

	    try {
	        Usuario usuario = (Usuario) authentication.getDetails();

	        Socio socio = this.repoSocio
	                         .findByUsuario_NombreUsuarioAndEliminadoFalse(authentication.getName())
	                         .orElseThrow(() -> new RuntimeException("El usuario no tiene un socio asociado"));
	        
	        System.out.println("SOCIO ID: " + socio.getId());

	        model.addAttribute("socio", socio);

	        Collection<CuotaMensual> listaCuotas = svcCuota.buscarPorSocio(socio);
	        model.addAttribute("listaCuotas", listaCuotas);
	        
	        BigDecimal deuda = BigDecimal.ZERO;
	        
	        for (CuotaMensual c : listaCuotas) {
	        	if (c.getEstado() == EstadoCuota.ADEUDADA) {
	        		deuda = deuda.add(c.getValorCuota().getValorCuota());
	        		
	        	}
	            
	        }
	        
	        DeudaForm deudaForm = new DeudaForm();
	        deudaForm.setIdSocio(socio.getId());
	        deudaForm.setDeuda(deuda);
	        
	        model.addAttribute("deudaForm", deudaForm);
	        model.addAttribute("deuda", deuda);
	        model.addAttribute("active", "socio-cuotas");

	    } catch (Exception e) {
	        model.addAttribute("msgError", "Error de Sistema: " + e.getMessage());
	    }

	    return "socio/deuda";
    }
    /*public String deuda(Authentication authentication, Model model) {
        String username = authentication != null ? authentication.getName() : "socio";
        model.addAttribute("titulo", "Mi deuda");
        model.addAttribute("seccion", "Socios");
        model.addAttribute("usuarioActual", username);
        model.addAttribute("active", "socio-deuda");
        return "socio/deuda";
    }*/
    
    @GetMapping("/homepage")
	public String irAHomepage(Authentication authentication, ModelMap model) {
				
		if (authentication == null || !authentication.isAuthenticated()) {
	        return "redirect:/login";
	    }
		
		Usuario usuario = (Usuario) authentication.getDetails();

        Socio socio = this.repoSocio
                         .findByUsuario_NombreUsuarioAndEliminadoFalse(authentication.getName())
                         .orElseThrow(() -> new RuntimeException("El usuario no tiene un socio asociado"));
        
        model.addAttribute("socio", socio);
		return "socio/homepage";
	}
}
