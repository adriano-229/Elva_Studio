package com.elva.tp1.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Departamento extends BaseEntity {

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer codigoPostal;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Provincia provincia;

    @OneToMany(mappedBy = "departamento", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Direccion> direcciones;
}
