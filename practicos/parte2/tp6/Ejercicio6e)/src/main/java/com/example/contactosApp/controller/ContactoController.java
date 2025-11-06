package com.example.contactosApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.contactosApp.domain.dto.ContactoDTO;
import com.example.contactosApp.facade.ContactoFacade;
import com.example.contactosApp.factory.ContactoFactoryDTO;
import com.example.contactosApp.service.impl.EmpresaServiceImpl;
import com.example.contactosApp.service.impl.PersonaServiceImpl;

import lombok.Getter;

@Controller
@Getter
@RequestMapping("/contacto")
public class ContactoController {

	private String viewList = "";
	private String viewEdit;
	private String redirectList;
	
	
    private final ContactoFacade contactoFacade;
	
	@Autowired
	private PersonaServiceImpl personaService;
	
	@Autowired
	private EmpresaServiceImpl empresaService;	
	
	
	public ContactoController(ContactoFacade contactoFacade) {
        this.contactoFacade = contactoFacade;
    }
	
	@GetMapping("/crear")
	public String crearContacto(@RequestParam(required = false) Long idPersona,
	                            @RequestParam(required = false) Long idEmpresa,
	                            @RequestParam String clase,
	                            Model model) {
	    try {
	        ContactoDTO contactoDTO = ContactoFactoryDTO.crearDTO(clase);

	        if (idPersona != null) {
	            contactoDTO.setPersona(personaService.findById(idPersona));
	        } else if (idEmpresa != null) {
	            contactoDTO.setEmpresa(empresaService.findById(idEmpresa));
	        }

	        model.addAttribute("entity", contactoDTO);
	        model.addAttribute("isNew", true);

	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("msgError", "Error de Sistema");
	    }

	    return getViewEdit(clase);
	}

	
	@GetMapping("/detalle/{id}/{clase}")
    public String detalle(@PathVariable Long id,
                          @PathVariable String clase,
                          Model model) {
        try {
            ContactoDTO contacto = contactoFacade.getDetalle(id, clase);
            contacto.setClase(contacto.getClass().getSimpleName());
            model.addAttribute("contacto", contacto);
            
        } catch (Exception e) {
            model.addAttribute("msgError", e.getMessage());
        }
        return "view/contacto/detalle";
    }
	
	private String getViewEdit(String clase) {
	    switch (clase.toLowerCase()) {
	        case "telefono":
	            return "view/contacto/eContactoT"; // nombre de tu template Thymeleaf
	        case "correo":
	            return "view/contacto/eContactoC";
	        default:
	            return getViewList(); // fallback
	    }
	}

}
