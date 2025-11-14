package com.projects.mycar.mycar_admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projects.mycar.mycar_admin.domain.AlquilerDTO;
import com.projects.mycar.mycar_admin.domain.ClienteDTO;
import com.projects.mycar.mycar_admin.domain.DetalleFacturaDTO;
import com.projects.mycar.mycar_admin.domain.FacturaDTO;
import com.projects.mycar.mycar_admin.domain.VehiculoDTO;
import com.projects.mycar.mycar_admin.domain.enums.EstadoVehiculo;
import com.projects.mycar.mycar_admin.service.impl.AlquilerServiceImpl;
import com.projects.mycar.mycar_admin.service.impl.ClienteServiceImpl;
import com.projects.mycar.mycar_admin.service.impl.FacturaServiceImpl;

import lombok.Getter;

@Controller
@RequestMapping("/factura")
@Getter
public class FacturaController extends BaseControllerImpl<FacturaDTO, FacturaServiceImpl>{
	
	@Autowired
	private AlquilerServiceImpl alquilerService;
	
	@Autowired 
	private ClienteServiceImpl clienteService;
	
	
	
	private String viewList = "view/pagos/listar";
	private String viewEdit = "";
	private String redirectList= "redirect:/factura/listar";
	
	@Override
	@GetMapping("/crear")
	public String crear(Model model) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@GetMapping("/listarPagosPendientes")
	public String listarPendientes(Model model) {
		
		try {
			
			List<FacturaDTO> facturas = servicio.obtenerPagosPendientes();
			model.addAttribute("facturas", facturas);
			
		} catch (Exception e) {
			e.printStackTrace();
            model.addAttribute("msgError", "Error de Sistema");
		}
		
		return getViewList();
		
	}
	
	/*@GetMapping("/anular/{id}")
	public String anular(@PathVariable Long id, Model model) {
	    try {
	        FacturaDTO factura = servicio.findById(id);
	        model.addAttribute("factura", factura);
	        
	        DetalleFacturaDTO detalle = factura.getDetalles().get(0);
	        
	        AlquilerDTO alquiler = alquilerService.findById(detalle.getAlquilerId());
	        
	        ClienteDTO cliente = alquiler.getCliente();
	        
	        model.addAttribute("cliente", alquiler.getCliente());
	        
	    } catch (Exception e) {
	        model.addAttribute("msgError", "No se pudo cargar el detalle del alquiler");
	        e.printStackTrace();
	        
	    }
	    return "view/pagos/detalle";
	}¨*/
	
	
	
	
	@GetMapping("/detalle/{id}")
	public String verDetalle(@PathVariable Long id, Model model) {
	    try {
	        FacturaDTO factura = servicio.findById(id);
	        model.addAttribute("factura", factura);
	        System.out.println("ESTOY EN FACTURA DETALLE");
	        
	        DetalleFacturaDTO detalle = factura.getDetalles().get(0);
	        
	        AlquilerDTO alquiler = alquilerService.findById(detalle.getAlquilerId());
	        
	        ClienteDTO cliente = alquiler.getCliente();
	        System.out.println("CLIENTE: " + cliente);
	        
	        model.addAttribute("cliente", alquiler.getCliente());
	        
	    } catch (Exception e) {
	        model.addAttribute("msgError", "No se pudo cargar el detalle del alquiler");
	        e.printStackTrace();
	        
	    }
	    return "view/pagos/detalle";
	}
	
	
	
	

}
