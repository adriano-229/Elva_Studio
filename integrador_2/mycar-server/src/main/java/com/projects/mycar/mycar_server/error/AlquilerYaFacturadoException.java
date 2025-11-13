package com.projects.mycar.mycar_server.error;

public class AlquilerYaFacturadoException extends RuntimeException {
    public AlquilerYaFacturadoException(Long alquilerId) {
        super("El alquiler " + alquilerId + " ya tiene factura asociada");
    }
}

