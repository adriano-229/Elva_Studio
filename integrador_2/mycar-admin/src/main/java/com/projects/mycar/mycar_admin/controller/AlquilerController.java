package com.projects.mycar.mycar_admin.controller;

import com.projects.mycar.mycar_admin.domain.*;
import com.projects.mycar.mycar_admin.domain.enums.EstadoVehiculo;
import com.projects.mycar.mycar_admin.service.impl.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/alquiler")
@Getter
public class AlquilerController extends BaseControllerImpl<AlquilerDTO, AlquilerServiceImpl>{
	
	@Autowired
	private VehiculoServiceImpl vehiculoService;
	
	@Autowired
	private ClienteServiceImpl clienteService;
	
	@Autowired
	private CodigoDescuentoServiceImpl codigoDescuentoService;
	
	@Autowired
	private CostoServiceImpl costoService;
	
	private final String viewList = "view/alquiler/listar";
	private final String viewEdit = "view/alquiler/eAlquiler";
	private final String redirectList= "redirect:/alquiler/listar";
	
	@Override
	@GetMapping("/crear")
	public String crear(Model model) {
		
		return null;
	}
	
	@PostMapping("/crearConCotizacion")
	public String crearAlquilerConCotizacion(@ModelAttribute AlquilerFormDTO alquilerCotizado,
												Model model) {
		
		try {
			
			
			AlquilerDTO alquiler = new AlquilerDTO();
			alquiler.setFechaDesde(alquilerCotizado.getFechaDesde());
			alquiler.setFechaHasta(alquilerCotizado.getFechaHasta());
			alquiler.setCantidadDias(alquilerCotizado.getCantidadDias());
			alquiler.setCostoCalculado(alquilerCotizado.getTotalConDescuento());
			alquiler.setVehiculoId(alquilerCotizado.getIdVehiculo());
			alquiler.setClienteId(alquilerCotizado.getIdCliente());
			
			VehiculoDTO vehiculo = vehiculoService.findById(alquilerCotizado.getIdVehiculo());
			model.addAttribute("vehiculoSeleccionado", vehiculo);
			
			ClienteDTO cliente = clienteService.findById(alquiler.getClienteId());
			model.addAttribute("cliente", cliente);
			
			DocumentacionDTO documentacion = new DocumentacionDTO();
			alquiler.setDocumentacion(documentacion);
			model.addAttribute("alquilerCotizado", alquilerCotizado);
			model.addAttribute("alquiler", alquiler);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msgError", "error al crear alquiler con cotizacion");
		}
		
		return viewEdit;
	}
	
	@GetMapping("/cotizar")
	public String cotizar(Model model) {
		
		try {
		
			List<VehiculoDTO> vehiculos = vehiculoService.buscarPorEstado(EstadoVehiculo.Disponible);
			AlquilerFormDTO alquilerCotizar = new AlquilerFormDTO();
			model.addAttribute("alquiler", alquilerCotizar);
			model.addAttribute("listaVehiculos", vehiculos);
			
			model.addAttribute("fechasDisponibles", List.of("2025-11-12", "2025-11-13", "2025-11-14", "2025-11-15", "2025-11-16", "2025-11-18"));

			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msgError", "error al cargar pag de cotizaciones");
		}
		
		return "view/alquiler/cotizar";
	}
	
	@PostMapping("/cotizar")
	public String cotizar(@ModelAttribute AlquilerFormDTO alquilerCotizar, 
							Model model) {
		
		try {
			
			if (alquilerCotizar.getCodigoDescuento() != null && !alquilerCotizar.getCodigoDescuento().isBlank()) {
				if (!codigoDescuentoService.existePorCodigo(alquilerCotizar.getCodigoDescuento())) {
					model.addAttribute("msgError", "El codigo de descuento no es válido");
				} else {
					model.addAttribute("codigoDescuento", alquilerCotizar.getCodigoDescuento());
				}
			}
			
			model.addAttribute("alquiler", alquilerCotizar);
			
			
			List<VehiculoDTO> vehiculos = vehiculoService.buscarPorEstado(EstadoVehiculo.Disponible);
			model.addAttribute("listaVehiculos", vehiculos);
			
			List<ClienteDTO> clientes = clienteService.findAll();
			model.addAttribute("listaClientes", clientes);
			
			model.addAttribute("fechasDisponibles", List.of("2025-11-12", "2025-11-15", "2025-11-18"));
			
			if (alquilerCotizar.getFechaDesde() == null 
				    || alquilerCotizar.getFechaHasta() == null
				    || alquilerCotizar.getFechaDesde().toString().isBlank()
				    || alquilerCotizar.getFechaHasta().toString().isBlank()) {
				    model.addAttribute("msgError", "Debe seleccionar al menos una fecha válida.");
				    return "view/alquiler/cotizar";
			}
			
			AlquilerFormDTO alquilerCotizado = costoService.cotizarAlquiler(alquilerCotizar);
			model.addAttribute("alquilerCotizado", alquilerCotizado);
			
			VehiculoDTO vehiculo = vehiculoService.findById(alquilerCotizado.getIdVehiculo());
			model.addAttribute("vehiculoSeleccionado", vehiculo);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msgError", "error al cotizar");
		}
		
		return "view/alquiler/cotizar";
	}
	
	@GetMapping("/detalle/{id}")
	public String verDetalle(@PathVariable Long id, Model model) {
	    try {
	        AlquilerDTO alquiler = servicio.findById(id);
	        model.addAttribute("alquiler", alquiler);
	        model.addAttribute("documentacion", alquiler.getDocumentacion());
	        
	        List<VehiculoDTO> vehiculos = vehiculoService.buscarPorEstado(EstadoVehiculo.Disponible);
	        model.addAttribute("listaVehiculos", vehiculos);
	        
	    } catch (Exception e) {
	        model.addAttribute("msgError", "No se pudo cargar el detalle del alquiler");
	        e.printStackTrace();
	        
	    }
	    return "view/alquiler/detalle";
	}

	@GetMapping("/buscar")
	public String buscarAlquileres( @RequestParam(required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate  fechaDesde,
								@RequestParam(required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate  fechaHasta,
								Model model) throws Exception{
		
		
		
		//Validaciones de fecha.
		LocalDate hoy = LocalDate.now();
		
		List<AlquilerDTO> lista = servicio.findAll();
		
	    if (fechaDesde != null && fechaHasta != null) {
	    	
	    	// 1. el orden de las fechas no es correcto --------------------------------------------------------
	        if (fechaDesde.isAfter(fechaHasta)) {
	            model.addAttribute("msgError", "La fecha 'Desde' no puede ser mayor que la fecha 'Hasta'.");
	            model.addAttribute("entities", lista);
	            return viewList;
					
			}
	        
	        // 2. La fecha hasta es mayor que la fecha actual -----------------------------------------------------
	        if (fechaHasta.isAfter(hoy.plusDays(1))) {
	            model.addAttribute("msgError", "La fecha 'Hasta' no puede ser mayor que la fecha actual.");
	            model.addAttribute("entities", lista);
	            return viewList;
	        }
	        
	        
	        // Las dos fechas estan correctas -------------------------------------------------------------------------
	        List<AlquilerDTO> alquileres = servicio.buscarPorPeriodo(fechaDesde, fechaHasta);
	        model.addAttribute("entities", alquileres);
	        return viewList;
	        
	   }
		   
	   model.addAttribute("msgError", "Debe ingresar ambas fechas para buscar por periodo");
	   model.addAttribute("entities", lista);
       return viewList;
	}
	   
}
