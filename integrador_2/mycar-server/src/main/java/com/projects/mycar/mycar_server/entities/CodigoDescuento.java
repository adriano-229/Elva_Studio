package com.projects.mycar.mycar_server.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "codigo_descuento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodigoDescuento extends Base {

    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(name = "porcentaje_descuento", nullable = false)
    private Double porcentajeDescuento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "fecha_generacion", nullable = false)
    private LocalDate fechaGeneracion;

    @Column(name = "fecha_expiracion")
    private LocalDate fechaExpiracion;

    @Column(name = "utilizado", nullable = false)
    private Boolean utilizado = false;

    @Column(name = "fecha_utilizacion")
    private LocalDate fechaUtilizacion;
}

