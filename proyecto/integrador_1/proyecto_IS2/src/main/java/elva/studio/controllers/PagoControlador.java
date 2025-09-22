package elva.studio.controllers;


import java.math.BigDecimal;
import java.time.LocalDateTime;
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

import com.mercadopago.client.MercadoPagoClient;
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

import elva.studio.dto.DeudaForm;
import elva.studio.entities.CuotaMensual;
import elva.studio.entities.FormaDePago;
import elva.studio.entities.Socio;
import elva.studio.entities.PagoOnline;
import elva.studio.enumeration.TipoPago;
import elva.studio.services.CuotaMensualService;
import elva.studio.services.PagoOnlineServicio;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/pagos")
public class PagoControlador {
	
	@Autowired
	private PagoOnlineServicio svcPagoOnline;
	
	@Autowired
	private CuotaMensualService svcCuota;
	
	@Value("${mercadopago.public-key}")
	private String publicKey;

    @Value("${ngrok.url}")
	private String ngrokUrl;
	//set NGROK_URL=https...
    
    @Autowired
    	PaymentClient paymentClient;
    
    private PagoOnline pagoOnline;
    
	@PostMapping("/confirmarDatos")
	public String pagarCuota(HttpSession session, @ModelAttribute DeudaForm deudaForm, Model model) throws Exception {
		
		try {
			
			//verifico que el usuario este logueado
			Socio socio = (Socio) session.getAttribute("socio");
			if (socio == null) {
		        return "redirect:/login";
		    }			
			model.addAttribute("socio", socio);
			
						
			// tomo el atributo idCuotas del duedaForm y lo paso a una lista de objetos CuotaMensual
			List<Long> idCuotas = deudaForm.getIdCuotas();
						
			Collection<CuotaMensual> cuotasAPagar = this.svcCuota.listarPorIds(idCuotas);
						
			// Agrego al modelo los valores de los atributos que vaya a necesitar en la vista
			model.addAttribute("totalAPagar", deudaForm.getTotalAPagar());
			
			// En el deudaForm recibo la formaPago dependiendo de eso el boton -confirmarDatos- carga las preferencias de mp o manda al usuario a transferencia
			
			// TRANSFERENCIA---------------------------------------------------------------------
			
			if (deudaForm.getFormaPago() == TipoPago.Transferencia) {
				
				return "transferencia";
				
			} else {
			// MERCADOPAGO-----------------------------------------------------------------------
				
				List<PreferenceItemRequest> items = new ArrayList<>();
				
				for (CuotaMensual c : cuotasAPagar) {
					PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
							.id(String.valueOf(c.getId()))
							.title("Cuota " + c.getMes() + "-" + c.getAnio())
					        .description("Membresía de sport gym - " + c.getMes())
					        .quantity(1)
					        .unitPrice(BigDecimal.valueOf(Math.round(c.getValorCuota().getValorCuota() * 100) / 100.0))
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
				                .success(url + "/pagos/pagoAceptado")
				                .failure(url + "/pagos/pagoPendiente")
				                .pending(url + "/pagos")
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
				pagoOnline = this.svcPagoOnline.crear(deudaForm.getIdSocio(), deudaForm.getTotalAPagar(), deudaForm.getIdCuotas(), deudaForm.getFormaPago());
				
				return "mercadoPago";
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
	
	@PostMapping("/transferencia")
	public String transferencia(@ModelAttribute DeudaForm deudaForm, @RequestParam("comprobante") MultipartFile archivo, Model model) {
	    if (archivo.isEmpty()) {
	        model.addAttribute("msgError", "No se seleccionó archivo");
	        return "pago";
	    }
	    // guardar archivo y procesar pago: El comrpobante ha sido recibido con exito, su pago esta siendo procesado
	    model.addAttribute("msgSuccess", "El comprobante ha sido recibido con exito, su pago esta siendo procesado...");
	    //creo model atribute para cada uno de los atributos del deuda 
	    //form para pasarlos a la pag del admin junto con el comprobante
	    
	    return "confirmacion";
	}
	
	
	// tu pago ha sido confirmado, ver factura.
	@GetMapping("/pagoAceptado")
	public String confirmarPago(HttpSession session, @RequestParam("payment_id")String paymentId, Model model) throws Exception{
		
		try {
			
			//verifico que el usuario este logueado
			Socio socio = (Socio) session.getAttribute("socio");
			if (socio == null) {
		        return "redirect:/login";
		    }			
			
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
		return "confirmacion";
	}
	
	
	
	
	@GetMapping("/pagoPendiente")
	public String pagoPendiente(HttpSession session,Model model) {
		
		try {
			
			Socio socio = (Socio) session.getAttribute("socio");
			if (socio == null) {
		        return "redirect:/login";
		    }	
			
			model.addAttribute("socio", socio);
			
			DeudaForm deudaForm = (DeudaForm) session.getAttribute("deudaForm");
			if (deudaForm == null) {
				return "redirect:/cuotas";
			}
			//model.addAttribute("deudaForm", deudaForm);
			
			Double totalAPagar = deudaForm.getTotalAPagar();
			
			model.addAttribute("totalAPagar", totalAPagar);
			
			String preferenceId = (String) session.getAttribute("preferenceId");
			
			model.addAttribute("preferenceId", preferenceId);
			model.addAttribute("publicKey", publicKey);
			
			return "mercadopago";
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/webhook")
	public ResponseEntity<String> respuestaPagoMP(HttpSession session, @RequestBody Map<String, Object> notificacion) {
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
	        if (this.svcPagoOnline.pagoExistente(paymentId)) {
	            return ResponseEntity.ok("El pago ya había sido procesado");
	        }
	        
	        	          
	        // este metodo procesa el pago, actualiza el estado de las cuotas y crea fcatura
	        this.svcPagoOnline.procesarPago(paymentId, status, pagoOnline);
	        
	        System.out.println("Pago aprobado y registrado: " + paymentId);

	        return ResponseEntity.ok("Pago recibido");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR");
	    }
	    
	}
	
	@PostMapping("confirmar-pago")
	public String confirmarPago() {
		
		return "factura";
	}
}
