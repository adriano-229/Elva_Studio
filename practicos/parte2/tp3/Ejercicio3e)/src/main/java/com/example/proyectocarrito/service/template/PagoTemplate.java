package com.example.proyectocarrito.service.template;

import com.example.proyectocarrito.domain.Carrito;

public abstract class PagoTemplate {

    // Template method
    public final void procesarPago(Carrito carrito) {
        validarCarrito(carrito);
        double total = calcularTotal(carrito);
        autenticar();
        ejecutarPago(total);
        registrarTransaccion(carrito, total);
        notificarUsuario(carrito);
    }

    // pasos fijos
    protected void validarCarrito(Carrito carrito) {
        if (carrito == null || carrito.getDetalles() == null || carrito.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("El carrito está vacío o no es válido");
        }
    }

    protected double calcularTotal(Carrito carrito) {
        return carrito.getDetalles().stream()
                .mapToDouble(d -> d.getPrecioUnitario() * d.getCantidad())
                .sum();
    }

    protected abstract void autenticar();
    protected abstract void ejecutarPago(double total);

    // pasos fijos
    protected void registrarTransaccion(Carrito carrito, double total) {
        System.out.println("Transacción registrada por un total de $" + total);
    }

    protected void notificarUsuario(Carrito carrito) {
        System.out.println("Correo enviado al usuario confirmando el pago.");
    }
}
