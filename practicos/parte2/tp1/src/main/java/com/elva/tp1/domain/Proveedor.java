package com.elva.tp1.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@PrimaryKeyJoinColumn
public class Proveedor extends Persona {

    @Column(unique = true, nullable = false)
    private String cuit;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Direccion direccion;
}
