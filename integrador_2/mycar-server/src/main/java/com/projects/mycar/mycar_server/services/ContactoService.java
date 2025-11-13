package com.projects.mycar.mycar_server.services;

import com.projects.mycar.mycar_server.dto.contacto.ContactoWhatsappResponse;

public interface ContactoService {

    ContactoWhatsappResponse obtenerWhatsappCliente(Long clienteId);
}
