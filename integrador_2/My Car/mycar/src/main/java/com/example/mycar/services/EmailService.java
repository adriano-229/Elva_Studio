package com.example.mycar.services;

public interface EmailService {
    void enviarCorreoPromocion(String destinatario, String nombreCliente, String codigoDescuento, Double porcentajeDescuento, String mensajePromocion);
}

