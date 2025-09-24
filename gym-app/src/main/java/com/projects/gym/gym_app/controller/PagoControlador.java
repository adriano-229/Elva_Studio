package com.projects.gym.gym_app.controller;


import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferencePaymentMethodsRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;

import org.springframework.ui.Model;
import com.projects.gym.gym_app.service.dto.DeudaForm;
import com.projects.gym.gym_app.service.dto.PagoOnline;
import com.projects.gym.gym_app.service.dto.PagoTransferencia;

import jakarta.servlet.http.HttpSession;

import com.projects.gym.gym_app.domain.CuotaMensual;
import com.projects.gym.gym_app.domain.Socio;
import com.projects.gym.gym_app.domain.Usuario;
import com.projects.gym.gym_app.domain.enums.TipoPago;
import com.projects.gym.gym_app.repository.SocioRepository;
import com.projects.gym.gym_app.service.CuotaMensualService;

import com.projects.gym.gym_app.service.SocioService;

import com.projects.gym.gym_app.service.PagoService;




@Controller
@RequestMapping("/pagos")
public class PagoControlador {
	
	@Autowired
	private CuotaMensualService svcCuotaMensual;
	
	@Autowired
	private SocioService svcSocio;
	
	@Autowired
	private PagoService svcPago;

	@Autowired
	private CuotaMensualService svcCuota;
	
	@Autowired
	private SocioRepository repoSocio;
	
	@Value("${mercadopago.public-key}")
	private String publicKey;

    @Value("${ngrok.url}")
	private String ngrokUrl;
	//set NGROK_URL=https...
    
    @Value("${app.upload.dir}")
    private String uploadDir;
    
    @Autowired
    PaymentClient paymentClient;
    
    private PagoOnline pagoOnline;
    
    private PagoTransferencia pagoTransferencia;

    /*@GetMapping("/mis-pagos")
	public String misPagos(Authentication authentication,@RequestParam Long numeroSocio, ModelMap model) {
		//logica para buscar pagos en el servicio
		Socio socio = svcSocio.buscarPorNrosocio(numeroSocio);
		if (socio == null) {
			return "redirect:/login";
		}
		
		if (authentication == null || !authentication.isAuthenticated()) {
	        return "redirect:/login";
	    }

			
		session.setAttribute("socio",socio);
		
		model.addAttribute("socio", socio);
		
		// Creo el DeudaForm y lo agrego al modelo
	    DeudaForm deudaForm = new DeudaForm();
	    deudaForm.setIdSocio(socio.getId());
	    deudaForm.setTotalAPagar(0.0); // o el total que corresponda
	    model.addAttribute("deudaForm", deudaForm);
	    model.addAttribute("tiposPago", TipoPago.values());
		return "pago"; 
    }*/
    

	@PostMapping("/confirmarDatos")
	public String pagarCuota(Authentication authentication, @ModelAttribute DeudaForm deudaForm, Model model, HttpSession session) throws Exception {
		
		if (authentication == null || !authentication.isAuthenticated()) {
	        return "redirect:/login";
	    }

		
		try {
			
			Usuario usuario = (Usuario) authentication.getDetails();

	        Socio socio = this.repoSocio
	                         .findByUsuario_NombreUsuarioAndEliminadoFalse(authentication.getName())
	                         .orElseThrow(() -> new RuntimeException("El usuario no tiene un socio asociado"));
	        
			model.addAttribute("socio", socio);
			model.addAttribute("deudaForm", deudaForm);
			
						
			// tomo el atributo idCuotas del duedaForm y lo paso a una lista de objetos CuotaMensual
			List<String> idCuotas = deudaForm.getIdCuotas();
						
			Collection<CuotaMensual> cuotasAPagar = this.svcCuota.listarPorIds(idCuotas);
						
			// Agrego al modelo los valores de los atributos que vaya a necesitar en la vista
			model.addAttribute("idSocio",socio.getId());
			model.addAttribute("totalAPagar", deudaForm.getTotalAPagar());
			
			// En el deudaForm recibo la formaPago dependiendo de eso el boton -confirmarDatos- carga las preferencias de mp o manda al usuario a transferencia
			
			// TRANSFERENCIA---------------------------------------------------------------------
			
			if (deudaForm.getFormaPago() == TipoPago.TRANSFERENCIA) {
				
				pagoTransferencia = PagoTransferencia.builder()
						.idSocio(deudaForm.getIdSocio())
						.idCuotas(deudaForm.getIdCuotas())
						.totalAPagar(deudaForm.getTotalAPagar())
						.tipoPago(deudaForm.getFormaPago())
						.build();
				
				session.setAttribute("deudaForm", deudaForm);
				session.setAttribute("pagoTransferencia", pagoTransferencia);
				
				
				return "pago/transferencia";
				
			} else if (deudaForm.getFormaPago() == TipoPago.BILLETERA_VIRTUAL){
			// MERCADOPAGO-----------------------------------------------------------------------
				
				List<PreferenceItemRequest> items = new ArrayList<>();
				
				for (CuotaMensual c : cuotasAPagar) {
					PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
							.id(String.valueOf(c.getId()))
							.title("Cuota " + c.getMes() + "-" + c.getAnio())
					        .description("Membresía de sport gym - " + c.getMes())
					        .quantity(1)
					        .unitPrice(c.getValorCuota().getValorCuota().setScale(2, RoundingMode.HALF_UP))
					        .currencyId("ARS")
					        .build();

					    items.add(itemRequest);
				}
				
				String url = ngrokUrl.trim();
				
				PreferencePaymentMethodsRequest paymentMethods = PreferencePaymentMethodsRequest.builder()
				        .installments(1)   // <- limita a 1 cuota
				        .build();
				
				PreferenceRequest preferenceRequest = PreferenceRequest.builder()
						.items(items)
						.paymentMethods(paymentMethods) 
						.backUrls(PreferenceBackUrlsRequest.builder()
				                .success(url + "/pagos/pagoAceptado?idSocio=" + socio.getId())
				                .failure(url + "/pagos/pagoPendiente?idSocio=" + socio.getId())
				                .pending(url + "/pagos/pagoPendiente?idSocio=" + socio.getId())
				                .build())
				        .autoReturn("approved")
						.notificationUrl(url + "/pagos/webhook")
						.statementDescriptor("SPORT GYM") 
						.build();
				
				PreferenceClient preferenceClient = new PreferenceClient();
				Preference preference = preferenceClient.create(preferenceRequest);
								
				model.addAttribute("publicKey", publicKey);
				model.addAttribute("preferenceId", preference.getId());
				
				session.setAttribute("deudaForm", deudaForm);
				session.setAttribute("preferenceId", preference.getId());
				
				// creo un objeto pagoOnline para guardar los datos del duedaForm pq sino los pierdo cuando llego a "/pagos/webhook"
				//pagoOnline = this.svcPagoOnline.crear(deudaForm.getIdSocio(), deudaForm.getTotalAPagar(), deudaForm.getIdCuotas(), deudaForm.getFormaPago());
				pagoOnline = PagoOnline.builder()
						.idSocio(deudaForm.getIdSocio())
						.idCuotas(deudaForm.getIdCuotas())
						.totalAPagar(deudaForm.getTotalAPagar())
						.tipoPago(deudaForm.getFormaPago())
						.build();
				
				session.setAttribute("pagoOnline", pagoOnline);
				
				return "pago/mercadoPago";
			} else {
				
				return "pago/efectivo";
			}
			
			
		} catch (MPApiException apiException) {
			
			System.out.println("API Error: " + apiException.getApiResponse().getContent());
		    throw apiException;
		} catch (MPException mpException) {
			mpException.printStackTrace();
			throw mpException;
		}catch (Exception e) {
			model.addAttribute("msgError", "Error de Sistema");
			return "pago";
		}
	}
	
	// Endpoint para renderizar la vista
	@GetMapping("/transferencia")
	public String mostrarTransferencia(Authentication authentication, HttpSession session, Model model) {
		if (authentication == null || !authentication.isAuthenticated()) {
	        return "redirect:/login";
	    }
		
		Usuario usuario = (Usuario) authentication.getDetails();

        Socio socio = this.repoSocio
                         .findByUsuario_NombreUsuarioAndEliminadoFalse(authentication.getName())
                         .orElseThrow(() -> new RuntimeException("El usuario no tiene un socio asociado"));
        

	    DeudaForm deudaForm = (DeudaForm) session.getAttribute("deudaForm");
	    model.addAttribute("socio", socio);
	    model.addAttribute("deudaForm", deudaForm);

	    return "pago/transferencia";
	}
	
	
	@PostMapping("/transferenciaComprobante")
	public String subirComprobante(Authentication authentication, HttpSession session,@ModelAttribute DeudaForm deudaForm, Model model) {
		
		if (authentication == null || !authentication.isAuthenticated()) {
	        return "redirect:/login";
	    }
		
		Usuario usuario = (Usuario) authentication.getDetails();

        Socio socio = this.repoSocio
                         .findByUsuario_NombreUsuarioAndEliminadoFalse(authentication.getName())
                         .orElseThrow(() -> new RuntimeException("El usuario no tiene un socio asociado"));
        

		
		System.out.println("ESTOY EN TRANSFERENCIA COMPROBANTE---------------------------------");
		
		//DeudaForm deudaForm = (DeudaForm) session.getAttribute("deudaForm");		
		System.out.println("DEUDAFORM: idSocio:" + deudaForm.getIdSocio());
		
		PagoTransferencia pagoTransferencia = (PagoTransferencia) session.getAttribute("pagoTransferencia");
		

		model.addAttribute("socio", socio);
		model.addAttribute("deudaForm", deudaForm);
        //model.addAttribute("deudaForm", deudaForm);
        
        MultipartFile archivo = deudaForm.getComprobante();
        
                
        System.out.println("DEUDA FORM:" + deudaForm.getIdCuotas());
		
		// Validación de archivo
	    if (archivo.isEmpty()) {
	        model.addAttribute("msgError", "No se seleccionó archivo");
	        return "pago/transferencia"; // nombre de tu HTML
	    }

	    // Validar extensión
	    String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();
	    if (nombreArchivo != null && !nombreArchivo.matches("(?i).+\\.(jpg|jpeg|pdf)$")) {
	    		deudaForm.setComprobante(null);
	    		model.addAttribute("msgError", "Solo se permiten archivos JPG o PDF");
	        return "pago/transferencia";
	    }

	    try {
	        // Guardar archivo en carpeta local
	        String rutaDestino = uploadDir; 
	        File carpeta = new File(rutaDestino);
	        if (!carpeta.exists()) carpeta.mkdirs();

	        Path pathDestino = Paths.get(rutaDestino, nombreArchivo);
	        archivo.transferTo(pathDestino.toFile());
	        
	        model.addAttribute("msgExito", "Archivo subido correctamente");
	        this.svcPago.procesarPagoTransferencia(pathDestino.toString(), pagoTransferencia);
	        
	        // deudaForm.setComprobantePath(pathDestino.toString());
	        // svcPago.guardarTransferencia(deudaForm);

	        model.addAttribute("msgExito", "Archivo subido correctamente");

	    } catch (Exception e) {
	        e.printStackTrace();
	        deudaForm.setComprobante(null);
	        model.addAttribute("msgError", "Error al subir archivo");
	        return "pago/transferencia";
	    }
	    return "pago/transferencia";
	}
	
	
	
	
	// tu pago ha sido confirmado, ver factura.
	@GetMapping("/pagoAceptado")
	public String confirmarPago(@RequestParam("idSocio")Long idSocio, @RequestParam("payment_id")String paymentId, Model model) throws Exception{
		
		//"/pagos/pagoAceptado"
		//Authentication authentication, 
		
		/*if (authentication == null || !authentication.isAuthenticated()) {
	        return "redirect:/login";
	    }*/
		
		try {
			
			/*Usuario usuario = (Usuario) authentication.getDetails();

	        Socio socio = this.repoSocio
	                         .findByUsuario_NombreUsuarioAndEliminadoFalse(authentication.getName())
	                         .orElseThrow(() -> new RuntimeException("El usuario no tiene un socio asociado"));*/
			
			Socio socio = repoSocio.findById(idSocio)
	                .orElseThrow(() -> new RuntimeException("Socio no encontrado"));

	        PaymentClient client = new PaymentClient();
			Payment payment = client.get(Long.valueOf(paymentId));
			
			model.addAttribute("socio", socio);
			
			model.addAttribute("monto", payment.getTransactionAmount());
			
			System.out.println("logo sport gym - ¡Muchas Gracias, " + socio.getNombre() + "! tu pago esta siendo procesado. Podras ver la factura correspondiente en tu cuenta una vez que sea aprobado");
			System.out.println("Monto abonado: " + payment.getTransactionAmount() + " ARS");
			
			
		} catch (MPApiException apiException) {
			
			System.out.println("API Error: " + apiException.getApiResponse().getContent());
		    throw apiException;
			
		} catch (MPException mpException) {
			
			mpException.printStackTrace();
			throw mpException;
			
		}catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		
		// logo sport gym - ¡Muchas Gracias, nombreSocio! tu pago esta siendo procesado. Podras ver tu factura en tu cuenta una vez que sea aporbado
		// Monto abonado:
		return "pago/confirmacion";
	}
	
	
	
	
	@GetMapping("/pagoPendiente")
	public String pagoPendiente(@RequestParam("idSocio")Long idSocio, HttpSession session,Model model) {
		
		/*if (authentication == null || !authentication.isAuthenticated()) {
	        return "redirect:/login";
	    }*/
		
		try {
			
			/*Usuario usuario = (Usuario) authentication.getDetails();

	        Socio socio = this.repoSocio
	                         .findByUsuario_NombreUsuarioAndEliminadoFalse(authentication.getName())
	                         .orElseThrow(() -> new RuntimeException("El usuario no tiene un socio asociado"));*/
			
			Socio socio = repoSocio.findById(idSocio)
	                .orElseThrow(() -> new RuntimeException("Socio no encontrado"));
	        
	        model.addAttribute("socio", socio);
			
			DeudaForm deudaForm = (DeudaForm) session.getAttribute("deudaForm");
			if (deudaForm == null) {
				return "redirect:/cuotas";
			}
			//model.addAttribute("deudaForm", deudaForm);
			
			BigDecimal totalAPagar = deudaForm.getTotalAPagar();
			
			model.addAttribute("totalAPagar", totalAPagar);
			
			String preferenceId = (String) session.getAttribute("preferenceId");
			
			model.addAttribute("preferenceId", preferenceId);
			model.addAttribute("publicKey", publicKey);
			
			return "pago/mercadopago";
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/webhook")
	public ResponseEntity<String> respuestaPagoMP(@RequestBody Map<String, Object> notificacion) {
	    try {
	    	
	    	System.out.println("Recibí notificación: " + notificacion);
	        String type = (String) notificacion.get("type");

	        // Solo procesamos pagos
	        if (!"payment".equals(type)) {
	            return ResponseEntity.ok("Notificación ignorada");
	        }

	        Map<String, Object> data = (Map<String, Object>) notificacion.get("data");
	        if (data == null || data.get("id") == null) {
	            return ResponseEntity.ok("Notificación sin ID, ignorada");
	        }
	        
	        Long paymentId = Long.valueOf(String.valueOf(data.get("id")));
	        
	        Payment payment = paymentClient.get(paymentId);
	        
	        
	        String status = payment.getStatus();
	        System.out.println("ESTADO" + payment.getStatus());
	        //System.out.println("Monto de la transaccion" + payment.getTransactionAmount());
	        
	        
	        if (!"approved".equalsIgnoreCase(status)) {
	            return ResponseEntity.ok("Pago no aprobado, no se procesa");
	        }
	        
	        
	        // Evitar procesar el mismo pago dos veces
	        //if (this.svcPagoOnline.pagoExistente(paymentId)) {
	        if (pagoOnline.getPaymentId() != null) {
	        		return ResponseEntity.ok("El pago ya había sido procesado");
	        }
	        
	        // este metodo procesa el pago, actualiza el estado de las cuotas y crea fcatura
	        this.svcPago.procesarPagoOnline(paymentId, status, pagoOnline);
	        
	        System.out.println("Pago aprobado y registrado: " + paymentId);

	        return ResponseEntity.ok("Pago recibido");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR");
	    }
	    
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
	
	/*@PostMapping("detalle-factura")
	public String confirmarPago(@RequestParam Long numeroSocio,
								//@RequestParam double valorCuota,
								//@RequestParam int cantidadCuotas,
								ModelMap model,
								HttpSession session) {
		
		// si esta todo ok me lleva a factura, sino me lleva a pago.html
		LocalDate fecha = LocalDate.now();
		Optional<Socio> optSocio = svcSocio.buscarPorNroSocio(numeroSocio);
		Socio socio = optSocio.get();
		
		//validar que socio no sea null
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
	}*/
	

}
