package com.example.mycar.error;

public class FacturaNoEncontradaException extends RuntimeException {
    public FacturaNoEncontradaException(Long facturaId) {
        super("No se encontró la factura con ID " + facturaId);
    }
}

