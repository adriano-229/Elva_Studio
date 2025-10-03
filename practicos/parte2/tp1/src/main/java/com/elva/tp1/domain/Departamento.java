package com.elva.tp1.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "departamento")
@Data
public class Departamento extends BaseEntity {

    @Column(nullable = false)
    private String nombre;

    @Column(name = "codigo_postal")
    private Integer codigoPostal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provincia_id", nullable = false)
    private Provincia provincia;
}
