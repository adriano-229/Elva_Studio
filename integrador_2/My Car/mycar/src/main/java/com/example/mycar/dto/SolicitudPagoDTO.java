package com.example.mycar.dto;

import com.example.mycar.enums.TipoPago;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO que recibe la solicitud de pago desde el frontend.
 * Contiene los IDs de los alquileres a pagar y la forma de pago seleccionada.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudPagoDTO {

    @NotEmpty(message = "Debe incluir al menos un alquiler para pagar")
    private List<Long> alquilerIds;

    @NotNull(message = "Debe especificar una forma de pago")
    private TipoPago tipoPago;

    private Long clienteId;
    private String observacion;
    private String comprobante; // Para transferencias o efectivo
}

