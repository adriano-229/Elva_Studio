package elva.studio.controllers;


import java.time.LocalDate;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.ModelMap;


import elva.studio.entities.CuotaMensual;
import elva.studio.entities.Direccion;
import elva.studio.entities.Socio;
import elva.studio.repositories.SocioRepositorio;
import elva.studio.services.CuotaMensualService;
import elva.studio.services.SocioService;
import jakarta.servlet.http.HttpSession;

import elva.studio.entities.Socio;
import elva.studio.repositories.SocioRepositorio;
import elva.studio.services.SocioService;


@Controller
@RequestMapping("/socio")
public class PagoControlador {
	
	@Autowired
	private SocioService svcSocio;
	
	@Autowired
	private SocioRepositorio repoSocio;

	@Autowired
	private CuotaMensualService svcCuotaMensual;
	

	@GetMapping("/mis-pagos")
	public String misPagos(@RequestParam Long numeroSocio, ModelMap model) {
		//logica para buscar pagos en el servicio
		Socio socio = svcSocio.buscarPorNrosocio(numeroSocio);
		if (socio == null) {
			return "redirect:/login";
		}
		model.addAttribute("socio", socio);
		return "pago2";
	}
	
	// datos a mostrar en detalle factura
	/*
	 * datos sucursal
	 * datos socio
	 * cantidad de cuotas a pagar
	 * valor precio cuota unitaria
	 * fecha vencimiento de la cuota
	 * metodo de pago elegido (Efectivo, Transferencia, Billetera_virtual)
	 * */
	
	@PostMapping("detalle-factura")
	public String confirmarPago(@RequestParam Long numeroSocio,
								//@RequestParam double valorCuota,
								//@RequestParam int cantidadCuotas,
								ModelMap model,
								HttpSession session) {
		
		// si esta todo ok me lleva a factura, sino me lleva a pago.html
		LocalDate fecha = LocalDate.now();
		Socio socio = svcSocio.buscarPorNrosocio(numeroSocio);
		
		// forma en la que pago el socio
		
		
		double valorCuota = svcCuotaMensual.valorCuotaPorSocio(socio);
		// esto deberia venir del pago
		int cantidadCuotas = 3;
		
	
		String nombre = socio.getNombre();
		String apellido = socio.getApellido();
		String direccion = socio.getDireccion().getBarrio();
		String numeroDocumento = socio.getNumeroDocumento();
		double total = valorCuota * cantidadCuotas;
		int bonificacion = 500;
		double neto = total - bonificacion;
		double iva = total * 0.21;
		double precioFinal = neto + iva;

		model.addAttribute("fecha",fecha);
		
		model.addAttribute("nombre",nombre);
		model.addAttribute("apellido",apellido);
		model.addAttribute("direccion",direccion);
		model.addAttribute("documento",numeroDocumento);
		
		model.addAttribute("valorCuota",valorCuota);
		model.addAttribute("total",total);
		model.addAttribute("bonificacion",bonificacion);
		model.addAttribute("neto",neto);
		model.addAttribute("iva",iva);
		model.addAttribute("precioFinal",precioFinal);
		
		//guardo la sesion para el pdf
		session.setAttribute("datosFactura", model);
		
		
		return "factura2";
	}
	

}
