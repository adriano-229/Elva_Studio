package com.example.mycar.error;

public class FacturaYaAnuladaException extends RuntimeException {
    public FacturaYaAnuladaException(Long facturaId) {
        super("La factura " + facturaId + " ya está anulada");
    }
}

