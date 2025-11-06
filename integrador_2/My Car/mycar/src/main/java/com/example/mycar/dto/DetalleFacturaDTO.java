package com.example.mycar.dto;

public record DetalleFacturaDTO(
        String alquilerId,
        Long facturaID,
        Double total,
        Double subtotal
) {
}