package com.example.contactosApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.contactosApp.domain.ContactoCorreoElectronico;
import com.example.contactosApp.domain.ContactoTelefonico;
import com.example.contactosApp.domain.dto.ContactoCorreoElectronicoDTO;
import com.example.contactosApp.domain.dto.ContactoTelefonicoDTO;
import com.example.contactosApp.domain.dto.EmpresaDTO;
import com.example.contactosApp.domain.dto.PersonaDTO;
import com.example.contactosApp.service.impl.ContactoCorreoEletronicoServiceImpl;
import com.example.contactosApp.service.impl.ContactoServiceImpl;
import com.example.contactosApp.service.impl.ContactoTelefonicoServiceImpl;
import com.example.contactosApp.service.impl.EmpresaServiceImpl;
import com.example.contactosApp.service.impl.PersonaServiceImpl;

import lombok.Getter;

@Controller
@Getter
@RequestMapping("/correo")
public class ContactoCorreoElectronicoController extends BaseControllerImpl<ContactoCorreoElectronico, ContactoCorreoElectronicoDTO, ContactoCorreoEletronicoServiceImpl>{
	
	@Autowired
	private PersonaServiceImpl personaService;
	
	@Autowired
	private EmpresaServiceImpl empresaService;
	
	private String viewList = "view/usuario/lUsuario";
	private String viewEdit = "view/contacto/eContactoC";
	private String redirectList;
	
	
	@GetMapping("/crearContactoPersona/{idPersona}")
	public String crearContactoPersona(@PathVariable Long idPersona, Model model) {
		try {
			
			ContactoCorreoElectronicoDTO contacto = new ContactoCorreoElectronicoDTO();
			PersonaDTO personaDTO = personaService.findById(idPersona);
			contacto.setPersona(personaDTO);
			model.addAttribute("entity", contacto);
			model.addAttribute("isNew", true);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msgError", "Error de Sistema"); 
			
		}
		return getViewEdit();
	}
	
	@GetMapping("/crearContactoEmpresa/{idEmpresa}")
	public String crearContactoEmpresa(@PathVariable Long idEmpresa, Model model) {
		try {
			
			ContactoCorreoElectronicoDTO contacto = new ContactoCorreoElectronicoDTO();
			EmpresaDTO empresaDTO = empresaService.findById(idEmpresa);
			contacto.setEmpresa(empresaDTO);
			model.addAttribute("entity", contacto);
			model.addAttribute("isNew", true);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msgError", "Error de Sistema"); 
			
		}
		return getViewEdit();
	}

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
	}

}
