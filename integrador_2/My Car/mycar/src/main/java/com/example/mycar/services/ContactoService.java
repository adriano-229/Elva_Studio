package com.example.mycar.services;

import com.example.mycar.dto.contacto.ContactoWhatsappResponse;

public interface ContactoService {

    ContactoWhatsappResponse obtenerWhatsappCliente(Long clienteId);
}
