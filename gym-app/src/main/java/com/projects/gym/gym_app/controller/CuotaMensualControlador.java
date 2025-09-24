package com.projects.gym.gym_app.controller;

import java.util.Date;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.projects.gym.gym_app.service.dto.DeudaForm;
import com.projects.gym.gym_app.domain.CuotaMensual;
import com.projects.gym.gym_app.domain.Socio;
import com.projects.gym.gym_app.domain.Usuario;
import com.projects.gym.gym_app.domain.enums.EstadoCuota;
import com.projects.gym.gym_app.domain.enums.TipoPago;
import com.projects.gym.gym_app.repository.SocioRepository;
import com.projects.gym.gym_app.service.CuotaMensualService;
import com.projects.gym.gym_app.service.SocioService;

import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/cuotas")
public class CuotaMensualControlador {
	
	@Autowired
	private CuotaMensualService svcCuota;
	
	@Autowired
	private SocioService svcSocio;
	
	@Autowired
	private SocioRepository repoSocio;
	
	
	@GetMapping
	public String listarCuotas(Authentication authentication, Model model) {
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

	        DeudaForm deudaForm = new DeudaForm();
	        deudaForm.setIdSocio(socio.getId());
	        model.addAttribute("deudaForm", deudaForm);
	        
	        listaCuotas.forEach(c -> System.out.println("DEUDA FORM CUOTAS: " + c.getValorCuota().getValorCuota()));
	        
	        

	        model.addAttribute("active", "socio-cuotas");

	    } catch (Exception e) {
	        model.addAttribute("msgError", "Error de Sistema: " + e.getMessage());
	    }

	    return "socio/cuotas";
	}
	
	
	@GetMapping("/buscar")
	public String buscarCuotas( Authentication authentication,
								@RequestParam(required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate  fechaDesde,
								@RequestParam(required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate  fechaHasta,
								@RequestParam(required = false)EstadoCuota estado,
								@ModelAttribute DeudaForm deudaForm,
								Model model) throws Exception{
		
		if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login"; // Por si alguien entra sin loguearse
        }
		
		Socio socio = this.repoSocio.findByUsuario_NombreUsuarioAndEliminadoFalse(authentication.getName())
									.orElseThrow(() -> new RuntimeException("El usuario no tiene un socio asociado"));
		
					
			model.addAttribute("socio", socio);
			model.addAttribute("deudaForm", deudaForm);
			
			List<CuotaMensual> listaCuotasSocio = this.svcCuota.buscarPorSocio(socio);	
			System.out.println("Fecha desde: " + fechaDesde);
			System.out.println("Fecha hasta: " + fechaHasta);
			System.out.println("Lista de cuotas (antes de validar): " + listaCuotasSocio.size());
			
			//Validaciones de fecha.
			LocalDate hoy = LocalDate.now();
			
		    if (fechaDesde != null && fechaHasta != null) {
		        if (fechaDesde.isAfter(fechaHasta)) {
		            model.addAttribute("error", "La fecha 'Desde' no puede ser mayor que la fecha 'Hasta'.");
		            model.addAttribute("listaCuotas", listaCuotasSocio);
		            System.out.println("Lista de cuotas: " + listaCuotasSocio.size());
		            return "socio/cuotas";
		        }
		        if (fechaHasta.isAfter(hoy.plusDays(1))) {
		            model.addAttribute("error", "La fecha 'Hasta' no puede ser mayor que la fecha actual.");
		            model.addAttribute("listaCuotas", listaCuotasSocio);
		            System.out.println("Lista de cuotas: " + listaCuotasSocio.size());
		            return "socio/cuotas";
		        }
		    }
			
			if (fechaDesde == null && fechaHasta == null) {
				
				if (estado == null) {
					return "redirect:/cuotas";
				}else {
					Collection<CuotaMensual> listaCuotas = this.svcCuota.listarPorEstado(socio, estado);
					model.addAttribute("listaCuotas", listaCuotas);
					
				}
				
			}else 
				if (fechaDesde == null || fechaHasta == null) {
					model.addAttribute("error", "Para filtrar por fecha ingrese ambas fechas");
					model.addAttribute("listaCuotas", listaCuotasSocio);
		            return "socio/cuotas";
					
				} else {
					if (estado != null) {
						Collection<CuotaMensual> listaCuotas = this.svcCuota.listarPorFechayEstado(socio, fechaDesde, fechaHasta, estado);
						model.addAttribute("listaCuotas", listaCuotas);
						
					}else {
						Collection<CuotaMensual> listaCuotas = this.svcCuota.listarPorFecha(socio, fechaDesde, fechaHasta);
						model.addAttribute("listaCuotas", listaCuotas);
						
					}
				}
			
		return "socio/cuotas";
		
	}
	
		
	@PostMapping("/pagar")
	public String pagarCuota(Authentication authentication, @ModelAttribute DeudaForm deudaForm, Model model) throws Exception {
		
		if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login"; // Por si alguien entra sin loguearse
        }
		
		Socio socio = this.repoSocio.findByUsuario_NombreUsuarioAndEliminadoFalse(authentication.getName())
									.orElseThrow(() -> new RuntimeException("El usuario no tiene un socio asociado"));
		
		
		model.addAttribute("socio", socio);
		
		List<String> idCuotas = deudaForm.getIdCuotas();
		
		if (idCuotas == null || idCuotas.isEmpty()) {
			model.addAttribute("deudaForm", new DeudaForm());
			model.addAttribute("msgError", "No ha seleccionado ninguna cuota");
			return "socio/cuotas";
		}
		
		Collection<CuotaMensual> cuotasAPagar = this.svcCuota.listarPorIds(idCuotas);
		
					
		model.addAttribute("cuotasAPagar", cuotasAPagar);
		
		BigDecimal totalAPagar = BigDecimal.ZERO;
		
		for (CuotaMensual c : cuotasAPagar) {
			totalAPagar = totalAPagar.add(c.getValorCuota().getValorCuota());
		}
		
		deudaForm.setTotalAPagar(totalAPagar);
		model.addAttribute("totalAPagar", totalAPagar);
		model.addAttribute("tiposPago", TipoPago.values());
        model.addAttribute("deudaForm", deudaForm);
	        
		return "pago/form";
		
		
	}
	
	

}
