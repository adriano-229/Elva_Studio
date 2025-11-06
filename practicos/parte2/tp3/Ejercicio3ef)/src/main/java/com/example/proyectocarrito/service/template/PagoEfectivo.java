package com.example.proyectocarrito.service.template;

import org.springframework.stereotype.Service;

@Service
public class PagoEfectivo extends PagoTemplate {

    @Override
    protected void autenticar() {
        System.out.println("No se requiere autenticación para pago en efectivo.");
    }

    @Override
    protected void ejecutarPago(double total) {
        System.out.println("Pago en efectivo registrado por $" + total);
    }
}
