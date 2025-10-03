package com.elva.tp1.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "proveedor")
@Data
@PrimaryKeyJoinColumn(name = "persona_id")
public class Proveedor extends Persona {

    @Column(unique = true, nullable = false)
    private String cuit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "direccion_id", nullable = false)
    private Direccion direccion;
}
