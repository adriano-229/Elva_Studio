package com.example.contactosApp.facade;

import org.springframework.stereotype.Component;

import com.example.contactosApp.domain.dto.ContactoDTO;
import com.example.contactosApp.service.impl.ContactoCorreoEletronicoServiceImpl;
import com.example.contactosApp.service.impl.ContactoTelefonicoServiceImpl;

@Component
public class ContactoFacade {

	 private ContactoTelefonicoServiceImpl telefonoService;
	
	 private ContactoCorreoEletronicoServiceImpl correoService;
	 
	 public ContactoFacade(ContactoTelefonicoServiceImpl telefonoService,
	                       ContactoCorreoEletronicoServiceImpl correoService) {
	     this.telefonoService = telefonoService;
	     this.correoService = correoService;
	 }
	
	 public ContactoDTO getDetalle(Long id, String clase) throws Exception {
	     switch(clase) {
	         case "telefono":
			     return telefonoService.findById(id);
			 case "correo":
			     return correoService.findById(id);
			 default:
			     throw new Exception("Tipo de contacto no soportado");
	     }
	 }
}
