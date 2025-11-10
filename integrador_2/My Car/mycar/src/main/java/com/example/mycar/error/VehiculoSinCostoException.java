package com.example.mycar.error;

public class VehiculoSinCostoException extends RuntimeException {
    public VehiculoSinCostoException(Long vehiculoId) {
        super("El vehículo " + vehiculoId + " no tiene costo definido");
    }
}

