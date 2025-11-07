package com.example.mycar.error;

public class AlquilerNoEncontradoException extends RuntimeException {
    public AlquilerNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

