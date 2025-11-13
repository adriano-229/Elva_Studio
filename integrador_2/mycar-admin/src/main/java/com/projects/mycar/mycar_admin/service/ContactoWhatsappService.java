package com.projects.mycar.mycar_admin.service;

import com.projects.mycar.mycar_admin.domain.contacto.ContactoWhatsappResponse;

public interface ContactoWhatsappService {

    ContactoWhatsappResponse obtenerWhatsapp(Long clienteId) throws Exception;

}
