package com.example.proyectocarrito.service.template;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class PagoMercadoPago extends PagoTemplate {

    @Override
    protected void autenticar() {
        System.out.println("Autenticando con MercadoPago...");
    }

    @Override
    protected void ejecutarPago(double total) {
        System.out.println("Pago realizado a través de la API de MercadoPago: $" + total);
    }
}
