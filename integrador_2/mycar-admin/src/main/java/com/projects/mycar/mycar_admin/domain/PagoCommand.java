package com.projects.mycar.mycar_admin.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PagoCommand {
    @NotBlank
    private String formaDePagoId;     // id de FormaDePago existente (EFECTIVO / TRANSFERENCIA / BILLETERA_VIRTUAL)
    private String observacion;       // ej: nro de comprobante, alias, etc.
    private String comprobante;       // opcional: referencia/txId (texto libre)
}