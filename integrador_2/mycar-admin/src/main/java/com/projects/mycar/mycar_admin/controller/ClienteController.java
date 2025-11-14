package com.projects.mycar.mycar_admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projects.mycar.mycar_admin.domain.ClienteDTO;
import com.projects.mycar.mycar_admin.domain.DireccionDTO;
import com.projects.mycar.mycar_admin.service.impl.ClienteServiceImpl;
import com.projects.mycar.mycar_admin.service.impl.DepartamentoServiceImpl;
import com.projects.mycar.mycar_admin.service.impl.LocalidadServiceImpl;
import com.projects.mycar.mycar_admin.service.impl.PaisServiceImpl;
import com.projects.mycar.mycar_admin.service.impl.ProvinciaServiceImpl;

import lombok.Getter;

@Controller
@RequestMapping("/clientes")
@Getter
public class ClienteController extends BaseControllerImpl<ClienteDTO, ClienteServiceImpl>{
	
	@Autowired
	private PaisServiceImpl paisService;
	
	@Autowired
	private ProvinciaServiceImpl provinciaService;
	
	@Autowired
	private DepartamentoServiceImpl departamentoService;
	
	@Autowired
	private LocalidadServiceImpl localidadService;
	
	private String viewList = "view/cliente/listar";
	private String viewEdit = "view/cliente/eCliente";
	private String redirectList= "redirect:/cliente/listar";
	
	@Override
	@GetMapping("/crear")
	public String crear(Model model) {
		
		try {
			
			DireccionDTO direccion = new DireccionDTO();
			ClienteDTO cliente = new ClienteDTO();
			cliente.setDireccion(direccion);
			model.addAttribute("cliente", cliente);
			model.addAttribute("listaPaises", paisService.findAll());
			model.addAttribute("listaDepartamentos", departamentoService.findAll());
			model.addAttribute("listaProvincias", provinciaService.findAll());
			model.addAttribute("listaLocalidades", localidadService.findAll());
			
			model.addAttribute("isNew", true);
			
			
		} catch (Exception e) {
			e.printStackTrace();
	        model.addAttribute("msgError", "Error de Sistema");
		}
		
		return viewEdit;
	}
	
	@GetMapping("/search")
	public String buscarPorDni(String numeroDocumento, Model model) {
		
		try {
			
			ClienteDTO cliente = servicio.buscarPorDni(numeroDocumento);
			
			if (cliente == null) {
				model.addAttribute("msgError", "El cliente no se encuentra registrado");
			} else {
				model.addAttribute("cliente", cliente);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msgError", "error al crear alquiler con cotizacion");
		}
		return "";
	}


}
