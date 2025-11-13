package com.projects.mycar.mycar_server.error;

public class AlquilerNoEncontradoException extends RuntimeException {
    public AlquilerNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

