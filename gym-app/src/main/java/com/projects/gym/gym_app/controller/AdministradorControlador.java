package com.projects.gym.gym_app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

import com.projects.gym.gym_app.service.CuotaMensualService;
import com.projects.gym.gym_app.service.SocioService;
import com.projects.gym.gym_app.service.dto.CuotaAdmin;

import jakarta.servlet.http.HttpSession;

import com.projects.gym.gym_app.service.FacturaService;
import com.projects.gym.gym_app.service.DetalleFacturaService;
import com.projects.gym.gym_app.service.FormaDePagoService;
import com.projects.gym.gym_app.domain.CuotaMensual;
import com.projects.gym.gym_app.domain.DetalleFactura;
import com.projects.gym.gym_app.domain.Factura;
import com.projects.gym.gym_app.domain.FormaDePago;
import com.projects.gym.gym_app.domain.Socio;
import com.projects.gym.gym_app.domain.enums.EstadoCuota;
import com.projects.gym.gym_app.domain.enums.EstadoFactura;
import com.projects.gym.gym_app.domain.enums.TipoPago;



@Controller
@RequestMapping("/admin/pagos")
public class AdministradorControlador {
	
	@Autowired
	private CuotaMensualService svcCuota;
	
	@Autowired
	private SocioService svcSocio;
	
	@Autowired
	private FacturaService svcFactura;
	
	@Autowired
	private DetalleFacturaService svcDetalle; //traer
	
	@Autowired
	private FormaDePagoService svcFormaPago; //traer
	
	
	@GetMapping
	public String listarCuotas(@RequestParam(required = false) Long numeroSocio,
	                           @RequestParam(required = false) String estado,
	                           ModelMap model) throws Exception {

	    List<CuotaMensual> cuotas = new ArrayList<>(); // siempre inicializada
	    EstadoCuota estadoEnum = null;
	    
	    // Convertir estado a Enum si no está vacío
	    if (estado != null && !estado.isEmpty()) {
	        try {
	            estadoEnum = EstadoCuota.valueOf(estado);
	        } catch (IllegalArgumentException e) {
	            System.out.println("Estado inválido: " + estado);
	        }
	    }

	    // Filtrado por número de socio y estado
	    if (numeroSocio != null && estadoEnum != null) {
	        Optional<Socio> optSocio = svcSocio.buscarPorNroSocio(numeroSocio);
	        if (optSocio.isPresent()) {
	            Socio socio = optSocio.get();
	            try {
	                cuotas = svcCuota.listarPorEstadoYSocio(socio, estadoEnum);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    } else if (numeroSocio != null) {
	        Optional<Socio> optSocio = svcSocio.buscarPorNroSocio(numeroSocio);
	        if (optSocio.isPresent()) {
	            Socio socio = optSocio.get();
	            System.out.println("NUMERO SOCIO: " + socio.getNumeroSocio());
	            try {
	                cuotas = svcCuota.listarActivos(socio);
	                System.out.println("CUOTAS: " + cuotas);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    } else if (estadoEnum != null) {
	        try {
	            cuotas = svcCuota.listarPorEstado(estadoEnum);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } else {
	        try {
	            cuotas = svcCuota.listarTodos();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    // Crear lista de DTO para la vista
	    List<CuotaAdmin> listaCuotasAdmin = new ArrayList<>();
	    for (CuotaMensual cuota : cuotas) {
	        TipoPago tipoPago = null;

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

	    model.addAttribute("listaCuotas", listaCuotasAdmin);
	    model.addAttribute("numeroSocio", numeroSocio);
	    model.addAttribute("estado", estado);

	    return "cuotasAdmin";
	}

	
	@PostMapping("/calcularTotal")
	public String calcularTotal(@RequestParam List<String> idCuotas, ModelMap model) {
		
		if (idCuotas == null || idCuotas.isEmpty()) {
	        return "redirect:/admin/pagos";
	    }
		
		try {
			List<CuotaMensual> cuotas = svcCuota.listarPorIds(idCuotas);;
			BigDecimal totalPagar = BigDecimal.ZERO;
			
			for (CuotaMensual cuota: cuotas) {
				totalPagar = totalPagar.add(cuota.getValorCuota().getValorCuota());
			}
			
			model.addAttribute("total",totalPagar);
			return "cuotasAdmin";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "cuotasAdmin";
		
		
	}
	
	
	@PostMapping("/pagar")
	public String pagarCuota(@RequestParam(required = false) List<String> idCuotas, HttpSession session,ModelMap model) throws Exception {
		
		if (idCuotas == null || idCuotas.isEmpty()) {
	        return "redirect:/admin/pagos";
	    }
		
		
		
		try {
			
			List<DetalleFactura> detalles = new ArrayList<>();
			List<CuotaMensual> cuotas = svcCuota.listarPorIds(idCuotas);
			//FormaDePago formaPago = null;
			
			BigDecimal totalAPagar = BigDecimal.ZERO;
			BigDecimal valorCuota = BigDecimal.ZERO;
			int cantCuotas = cuotas.size();
			
			for (CuotaMensual cuota : cuotas) {
		        totalAPagar = totalAPagar.add(cuota.getValorCuota().getValorCuota());
		        valorCuota = cuota.getValorCuota().getValorCuota();
		        
		        DetalleFactura detalle = new DetalleFactura();
		        detalle.setCuotaMensual(cuota);
		        detalles.add(detalle);
		        
		        
		        cuota.setEstado(EstadoCuota.PAGADA);
		        svcCuota.actualizar(cuota, cuota.getId());}
			
			// obtengo el tipo de pago temporal guardado por el socio
		    TipoPago tipoPago = (TipoPago) session.getAttribute("tipoPago");
		    FormaDePago formaPago = svcFormaPago.buscarPorTipoPago(tipoPago);

		    // creo la factura
		    Date fecha = new Date();
		    Long numeroFactura = System.currentTimeMillis();
		    
		    Socio socio = cuotas.get(0).getSocio();
		    
		    svcFactura.crearFactura(numeroFactura, socio, fecha, totalAPagar, EstadoFactura.PAGADA, detalles, formaPago);

		 // suponiendo que totalAPagar ya es un BigDecimal
		    BigDecimal iva = totalAPagar
		            .multiply(new BigDecimal("0.21"))     // calcular 21%
		            .setScale(2, RoundingMode.HALF_UP);   // redondear a 2 decimales

		    BigDecimal neto = totalAPagar.subtract(iva); // restar IVA

		    BigDecimal precioFinal = totalAPagar;        // queda igual que el total

		    
		    
		    // datos que necesita la factura: 
		    
		    model.addAttribute("detalles", detalles);
		    model.addAttribute("total", totalAPagar);
		    model.addAttribute("valorCuota", valorCuota);
		    model.addAttribute("cantidad", cantCuotas);
		    model.addAttribute("bonificacion", 0); // si no hay bonificación
		    model.addAttribute("neto", neto);
		    model.addAttribute("iva", iva);
		    model.addAttribute("precioFinal", precioFinal);
		    model.addAttribute("formaPago", formaPago);

		    model.addAttribute("fecha", fecha);
		    model.addAttribute("numeroFactura", numeroFactura);

		    model.addAttribute("nombre", socio.getNombre());
		    model.addAttribute("apellido", socio.getApellido());
		    model.addAttribute("direccion", socio.getDireccion().getCalle());
		    model.addAttribute("documento", socio.getNumeroDocumento());

		    return "factura2";
			
			
		} catch (Exception e) {
				e.printStackTrace();
		}

	    return "cuotasAdmin";

	}
	
	
	

}
