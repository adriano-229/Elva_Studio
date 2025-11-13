package com.example.mycar.dto.contacto;

/**
 * DTO simple para devolver la URL de WhatsApp Web.
 */
public record ContactoWhatsappResponse(
        Long clienteId,
        String telefono,
        String waUrl
) {
}
