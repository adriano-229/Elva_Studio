package com.projects.mycar.mycar_admin.domain.contacto;

/**
 * DTO simple para devolver la URL de WhatsApp Web.
 */
public record ContactoWhatsappResponse(
        Long clienteId,
        String telefono,
        String waUrl
) {
}
