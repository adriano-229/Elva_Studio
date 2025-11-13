package com.projects.mycar.mycar_admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mycar.mycar_admin.domain.contacto.ContactoWhatsappResponse;
import com.projects.mycar.mycar_admin.dao.impl.ContactoWhatsAppRestDaoImpl;
import com.projects.mycar.mycar_admin.service.ContactoWhatsappService;

@Service
public class ContactoWhatsappServiceImpl implements ContactoWhatsappService{
	
	@Autowired
	private ContactoWhatsAppRestDaoImpl daoContactoWhatsapp;

	@Override
	public ContactoWhatsappResponse obtenerWhatsapp(Long clienteId) throws Exception {
		try {
			return daoContactoWhatsapp.obtenerWhatsapp(clienteId);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	

}
