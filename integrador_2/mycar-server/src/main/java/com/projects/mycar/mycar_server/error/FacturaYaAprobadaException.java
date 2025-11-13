package com.projects.mycar.mycar_server.error;

public class FacturaYaAprobadaException extends RuntimeException {
    public FacturaYaAprobadaException(Long facturaId) {
        super("La factura " + facturaId + " ya está aprobada");
    }
}

