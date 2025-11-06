package com.projects.gym.gym_app.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PagoCommand {
    @NotBlank
    private String formaDePagoId;     // id de FormaDePago existente (EFECTIVO / TRANSFERENCIA / BILLETERA_VIRTUAL)
    private String observacion;       // ej: nro de comprobante, alias, etc.
    private String comprobante;       // opcional: referencia/txId (texto libre)
}