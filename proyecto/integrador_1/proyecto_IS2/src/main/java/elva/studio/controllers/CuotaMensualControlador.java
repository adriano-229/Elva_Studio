package elva.studio.controllers;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import elva.studio.entities.CuotaMensual;
import elva.studio.entities.Socio;
import elva.studio.enumeration.EstadoCuota;
import elva.studio.services.CuotaMensualService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cuotas")
public class CuotaMensualControlador {
	
	@Autowired
	CuotaMensualService svcCuota;
	
		
	@GetMapping
	public String listarCuotas(HttpSession session, Model model) {
		try {
			Socio socio = (Socio) session.getAttribute("socio");
		    if (socio == null) {
		        return "redirect:/login";
		    }
		    
			Collection<CuotaMensual> listaCuotas = this.svcCuota.buscarPorSocio(socio);
			//Collection<CuotaMensual> listaCuotas = this.svcCuota.listarActivos();
			model.addAttribute("listaCuotas", listaCuotas);
				
		} catch (Exception e) {
			model.addAttribute("msgError", "Error de Sistema");
			
		}
		
		return "cuotas";
	}
	
	@GetMapping("/buscar")
	public String buscarCuotas( HttpSession session,
								@RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaDesde,
								@RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaHasta,
								@RequestParam(required = false)EstadoCuota estado, 
								Model model) throws Exception{
		
		try {
			Socio socio = (Socio) session.getAttribute("socio");
			if (socio == null) {
		        return "redirect:/login";
		    }
			
			if (fechaDesde == null || fechaHasta == null) {
				
				if (estado == null) {
					return "redirect:/cuotas";
				}else {
					Collection<CuotaMensual> listaCuotas = this.svcCuota.listarPorEstado(socio, estado);
					model.addAttribute("listaCuotas", listaCuotas);
					
				}
				
			}else 
				if (estado != null) {
					Collection<CuotaMensual> listaCuotas = this.svcCuota.listarPorFechayEstado(socio, fechaDesde, fechaHasta, estado);
					model.addAttribute("listaCuotas", listaCuotas);
					
				}else {
					Collection<CuotaMensual> listaCuotas = this.svcCuota.listarPorFecha(socio, fechaDesde, fechaHasta);
					model.addAttribute("listaCuotas", listaCuotas);
					
				}
			
			
			
		} catch (Exception e) {
			model.addAttribute("msgError", "Error de Sistema");
		}
		
		return "cuotas";
		
	}
	
		
	@PostMapping("/pagar")
	public String pagarCuota(HttpSession session, @RequestParam(required = false)List<Long> idCuotas, Model model) throws Exception {
		
		try {
			Socio socio = (Socio) session.getAttribute("socio");
			if (socio == null) {
		        return "redirect:/login";
		    }
			
			if (idCuotas == null || idCuotas.isEmpty()) {
				model.addAttribute("msgError", "No ha seleccionado ninguna cuota");
				return "cuotas";
			}
				
			Collection<CuotaMensual> cuotasAPagar = this.svcCuota.listarPorIds(idCuotas);
			
			model.addAttribute("cuotasAPagar", cuotasAPagar);
			
			double totalAPagar = 0;
			
			for (CuotaMensual c : cuotasAPagar) {
				totalAPagar += c.getValorCuota().getValorCuota();
			}
			
			model.addAttribute("totalAPagar", totalAPagar);
			model.addAttribute("socio", socio);
		
		} catch (Exception e) {
			model.addAttribute("msgError", "Error de Sistema");
		}
		
		return "pago";
		
		
	}
	
	

}
