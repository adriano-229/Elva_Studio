package com.example.mycar.error;

public class AlquilerYaFacturadoException extends RuntimeException {
    public AlquilerYaFacturadoException(Long alquilerId) {
        super("El alquiler " + alquilerId + " ya tiene factura asociada");
    }
}

