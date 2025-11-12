package com.example.mycar.mycar_admin.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

import com.example.mycar.mycar_admin.domain.enums.TipoPago;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Pago {

    private Long idSocio;

    private Double totalAPagar;

    private List<String> idCuotas;

    private TipoPago tipoPago;

    private LocalDateTime fechaRecepcion;

}
