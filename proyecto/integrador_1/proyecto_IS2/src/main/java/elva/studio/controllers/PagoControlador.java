package elva.studio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.ModelMap;

import elva.studio.entities.Socio;
import elva.studio.repositories.SocioRepositorio;
import elva.studio.services.SocioServicio;

@Controller
@RequestMapping("/socio")
public class PagoControlador {
	
	@Autowired
	private SocioServicio svcSocio;
	
	@Autowired
	private SocioRepositorio repoSocio;
	
	@GetMapping("/mis-pagos")
	public String misPagos(@RequestParam Long numeroSocio, ModelMap model) {
		//logica para buscar pagos en el servicio
		Socio socio = svcSocio.buscarPorNrosocio(numeroSocio);
		if (socio == null) {
			return "redirect:/login";
		}
		model.addAttribute("socio", socio);
		return "pago";
	}
	
	@PostMapping("confirmar-pago")
	public String confirmarPago() {
		
		return "factura";
	}
}
