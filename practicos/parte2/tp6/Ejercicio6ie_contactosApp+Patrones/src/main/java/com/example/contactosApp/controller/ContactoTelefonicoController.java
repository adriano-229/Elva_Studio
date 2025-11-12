package com.example.contactosApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.contactosApp.domain.ContactoTelefonico;
import com.example.contactosApp.domain.dto.ContactoTelefonicoDTO;
import com.example.contactosApp.service.impl.ContactoTelefonicoServiceImpl;
import com.example.contactosApp.service.impl.EmpresaServiceImpl;
import com.example.contactosApp.service.impl.PersonaServiceImpl;

import lombok.Getter;

@Controller
@Getter
@RequestMapping("/telefono")
public class ContactoTelefonicoController extends BaseControllerImpl<ContactoTelefonico, ContactoTelefonicoDTO, ContactoTelefonicoServiceImpl> {
	
	@Autowired
	private PersonaServiceImpl personaService;
	
	@Autowired
	private EmpresaServiceImpl empresaService;
	
	private String viewList = "view/usuario/lUsuario";
	private String viewEdit = "view/contacto/eContactoT";
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
	
	
	/*@Override
	@GetMapping("/detalle/{id}")
	public String detalle(@PathVariable Long id, Model model) {
		try {
			ContactoTelefonicoDTO contacto = servicio.findById(id);
			model.addAttribute("contacto", contacto);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msgError", "Error al cargar el detalle del contacto");
		}
		
		return "view/contacto/detalle";
		
	}

	@Override
	public String crear(Model model) {
		// TODO Auto-generated method stub
		return null;
	}*/
	
}

	


