package elva.studio.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import elva.studio.entities.CuotaMensual;
import elva.studio.entities.Socio;
import elva.studio.enumeration.TipoPago;
import elva.studio.services.CuotaMensualService;
import elva.studio.services.PagoServicio;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/pagos")
public class PagoControlador {
	
	@Autowired
	private PagoServicio svcPago;
	
	@Autowired
	private CuotaMensualService svcCuota;
	
	@PostMapping("/confirmarDatos")
	public String pagarCuota(HttpSession session, @RequestParam(required = false)List<Long> idCuotas,@RequestParam(required=true)Double totalAPagar, @RequestParam(required = true)TipoPago formaPago, Model model) throws Exception {
		
		try {
			Socio socio = (Socio) session.getAttribute("socio");
			if (socio == null) {
		        return "redirect:/login";
		    }			
			
			Collection<CuotaMensual> cuotasAPagar = this.svcCuota.listarPorIds(idCuotas);
			
			model.addAttribute("cuotasAPagar", cuotasAPagar);
			
			double total = 0;
			
			for (CuotaMensual c : cuotasAPagar) {
				total += c.getValorCuota().getValorCuota();}
			
			model.addAttribute("totalAPagar", total);
			model.addAttribute("socio", socio);
		
		} catch (Exception e) {
			model.addAttribute("msgError", "Error de Sistema");
		}
		
		if (formaPago == TipoPago.Transferencia) {
			return "transferencia";
		} else {
			return "mercadoPago";
		}
		
	}
	
	@PostMapping("/transferencia")
	public String transferencia(@RequestParam("comprobante") MultipartFile archivo, Model model) {
	    if (archivo.isEmpty()) {
	        model.addAttribute("msgError", "No se seleccionó archivo");
	        return "pago";
	    }
	    // guardar archivo y procesar pago: El comrpobante ha sido recibido con exito, su pago esta siendo procesado
	    model.addAttribute("msgSuccess", "El comprobante ha sido recibido con exito, su pago esta siendo procesado...");
	    return "confirmacion";
	}
	
	@PostMapping("/mercadopago")
	public String subirComprobante(Model model) {
		
	    return "confirmacion";
	}

	
	
	/*@GetMapping("/mis-pagos")
	public String misPagos(@RequestParam Long numeroSocio, ModelMap model) {
		//logica para buscar pagos en el servicio
		Socio socio = svcSocio.buscarPorNrosocio(numeroSocio);
		if (socio == null) {
			return "redirect:/login";
		}
		model.addAttribute("socio", socio);
		return "pago";
	}*/
	
	@PostMapping("confirmar-pago")
	public String confirmarPago() {
		
		return "factura";
	}
}
