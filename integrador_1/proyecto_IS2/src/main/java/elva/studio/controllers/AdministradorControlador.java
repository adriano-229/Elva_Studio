package elva.studio.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import elva.studio.dto.CuotaAdmin;
import elva.studio.dto.PagoOnline;
import elva.studio.entities.CuotaMensual;
import elva.studio.entities.DetalleFactura;
import elva.studio.entities.Factura;
import elva.studio.entities.FormaDePago;
import elva.studio.entities.Socio;
import elva.studio.enumeration.EstadoCuota;
import elva.studio.enumeration.EstadoFactura;
import elva.studio.enumeration.TipoPago;
import elva.studio.error.ErrorServicio;
import elva.studio.services.CuotaMensualService;
import elva.studio.services.DetalleFacturaService;
import elva.studio.services.FacturaService;
import elva.studio.services.FormaDePagoService;
import elva.studio.services.SocioService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/cuotas")
public class AdministradorControlador {
	
	@Autowired
	private CuotaMensualService svcCuota;
	
	@Autowired
	private SocioService svcSocio;
	
	@Autowired
	private FacturaService svcFactura;
	
	@Autowired
	private DetalleFacturaService svcDetalle;
	
	@Autowired
	private FormaDePagoService svcFormaPago;
	
	
	@GetMapping
	public String listarCuotas(@RequestParam(required = false) Long numeroSocio,
						        @RequestParam(required = false) String estado,
						        ModelMap model) throws ErrorServicio {
		
		List<CuotaMensual> cuotas = null;

		
		if (numeroSocio != null && estado != null && !estado.isEmpty()) {
	        Socio socio = svcSocio.buscarPorNrosocio(numeroSocio);
	        if (socio != null) {
	            try {
					cuotas = svcCuota.listarPorEstadoID(socio.getId(), estado);
				} catch (Exception e) {
					e.printStackTrace();
				}
	        }
	    } else if (numeroSocio != null) {
	        Socio socio = svcSocio.buscarPorNrosocio(numeroSocio);
	        if (socio != null) {
	            try {
					cuotas = svcCuota.buscarPorIDSocio(socio.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
	        }
	    } else if (estado != null && !estado.isEmpty()) {
	        cuotas = svcCuota.buscarPorEstado(estado);
	    } else {
	        try {
				cuotas = svcCuota.listarTodos();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
		
		List<CuotaAdmin> listaCuotasAdmin = new ArrayList<>();
		
		//para traerme la forma de pago del socio
		for (CuotaMensual cuota : cuotas) {
	        TipoPago tipoPago = null;

	        //  detalleFactura de esta cuota
	        List<DetalleFactura> detalles = svcDetalle.buscarPorCuota(cuota.getId());
	        if (!detalles.isEmpty()) {
	            Factura factura = detalles.get(0).getFactura();
	            if (factura != null && factura.getFormaDePago() != null) {
	            	tipoPago = factura.getFormaDePago().getTipoPago();
	            }
	        }

	        CuotaAdmin cuotaDto = new CuotaAdmin();
	        cuotaDto.setCuota(cuota);
	        cuotaDto.setTipoPago(tipoPago);
	        listaCuotasAdmin.add(cuotaDto);
	    }
		

		model.addAttribute("listaCuotas",listaCuotasAdmin);
		model.addAttribute("numeroSocio", numeroSocio);
		model.addAttribute("estado", estado);
		return "cuotasAdmin";
	}
	
	@PostMapping("/calcularTotal")
	public String calcularTotal(@RequestParam List<Long> idCuotas, ModelMap model) {
		
		if (idCuotas == null || idCuotas.isEmpty()) {
	        return "redirect:/admin/cuotas";
	    }
		
		List<CuotaMensual> cuotas = svcCuota.buscarPorIds(idCuotas);
		double totalPagar = 0;
		for (CuotaMensual cuota: cuotas) {
			double valor = cuota.getValorCuota().getValorCuota();
			totalPagar += valor;
		}
		model.addAttribute("total",totalPagar);
		return "cuotasAdmin";
	}
	
	
	@PostMapping("/pagar")
	public String pagarCuota(@RequestParam List<Long> idCuotas, HttpSession session,ModelMap model) throws ErrorServicio {
		
		if (idCuotas == null || idCuotas.isEmpty()) {
	        return "redirect:/admin/cuotas";
	    }
		
		List<DetalleFactura> detalles = new ArrayList<>();
		List<CuotaMensual> cuotas = svcCuota.buscarPorIds(idCuotas);
		//FormaDePago formaPago = null;
		
		double totalApagar = 0;
		double valorCuota = 0;
		int cantCuotas = cuotas.size();
		
		for (CuotaMensual cuota : cuotas) {
	        totalApagar += cuota.getValorCuota().getValorCuota();
	        valorCuota = cuota.getValorCuota().getValorCuota();
	        DetalleFactura detalle = new DetalleFactura();
	        detalle.setCuotaMensual(cuota);
	        detalles.add(detalle);
	        
	        
	        cuota.setEstado(EstadoCuota.Pagada);
	        try {
				svcCuota.actualizar(cuota, cuota.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }

	    // obtengo el tipo de pago temporal guardado por el socio
	    TipoPago tipoPago = (TipoPago) session.getAttribute("pagoEfectivo");
	    FormaDePago formaPago = svcFormaPago.buscarPorTipoPago(tipoPago);

	    // creo la factura
	    Date fecha = new Date();
	    Long numeroFactura = System.currentTimeMillis();
	    
	    svcFactura.crearFactura(numeroFactura, fecha, totalApagar, EstadoFactura.Pagada, detalles, formaPago);

	    double iva = Math.round(totalApagar * 0.21 * 100.0) / 100.0;
	    double neto = totalApagar - iva;
	    double precioFinal = totalApagar;
	    
	    Socio socio = cuotas.get(0).getSocio();
	    // datos que necesita la factura: 
	    
	    model.addAttribute("detalles", detalles);
	    model.addAttribute("total", totalApagar);
	    model.addAttribute("valorCuota", valorCuota);
	    model.addAttribute("cantidad", cantCuotas);
	    model.addAttribute("bonificacion", 0); // si no hay bonificación
	    model.addAttribute("neto", neto);
	    model.addAttribute("iva", iva);
	    model.addAttribute("precioFinal", precioFinal);
	    model.addAttribute("formaPago", formaPago.getTipoPago());

	    model.addAttribute("fecha", fecha);
	    model.addAttribute("numeroFactura", numeroFactura);

	    model.addAttribute("nombre", socio.getNombre());
	    model.addAttribute("apellido", socio.getApellido());
	    model.addAttribute("direccion", socio.getDireccion().getCalle());
	    model.addAttribute("documento", socio.getNumeroDocumento());

	    return "factura2";

	}
	
	
	

}
