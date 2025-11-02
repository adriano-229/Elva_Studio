package com.example.contactosApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.contactosApp.domain.ContactoCorreoElectronico;
import com.example.contactosApp.domain.dto.ContactoCorreoElectronicoDTO;
import com.example.contactosApp.service.impl.ContactoCorreoEletronicoServiceImpl;

import lombok.Getter;

@Controller
@Getter
@RequestMapping("/correo")
public class ContactoCorreoElectronicoController extends BaseControllerImpl<ContactoCorreoElectronico, ContactoCorreoElectronicoDTO, ContactoCorreoEletronicoServiceImpl>{

	private String viewList;
	private String viewEdit = "view/contacto/eContactoC";
	private String redirectList;
	
	@Override
	public String crear(Model model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String detalle(Long id, Model model) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*@Autowired
	private PersonaServiceImpl personaService;
	
	@Autowired
	private EmpresaServiceImpl empresaService;
	
	private String viewList = "view/usuario/lUsuario";
	private String viewEdit = "view/contacto/eContactoC";
	private String redirectList;
	
	
	@Override
	@GetMapping("/detalle/{id}")
	public String detalle(@PathVariable Long id, Model model) {
		try {
			System.out.println("contacto id: " + id);
			ContactoCorreoElectronicoDTO contacto = servicio.findById(id);
			System.out.println("contacto detalle: " + contacto);
			model.addAttribute("contacto", contacto);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msgError", "Error al cargar el detalle de la empresa");
		}
		
		return "view/contacto/detalle";
		
	}

	@Override
	public String crear(Model model) {
		// TODO Auto-generated method stub
		return null;
	}*/

}
