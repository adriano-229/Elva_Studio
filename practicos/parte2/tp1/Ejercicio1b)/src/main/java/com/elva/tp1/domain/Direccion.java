package com.elva.tp1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Direccion extends BaseEntity {

    @Column(nullable = false)
    private String calle;

    @Column(nullable = false)
    private Integer altura;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Departamento departamento;
}
