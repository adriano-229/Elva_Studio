package com.elva.tp1.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "proveedor")
@Data
public class Proveedor extends Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona")
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_direccion")
    private Direccion direccion;

    private String cuit;
}
