package com.projects.mycar.mycar_server.services;

public interface EmailService {
    void enviarCorreoPromocion(String destinatario, String nombreCliente, String codigoDescuento, Double porcentajeDescuento, String mensajePromocion);
}

