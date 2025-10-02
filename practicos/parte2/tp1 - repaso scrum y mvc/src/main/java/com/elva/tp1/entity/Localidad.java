package com.elva.tp1.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "localidad")
@Data
public class Localidad extends BaseActivable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_departamento", nullable = false)
    private Departamento departamento;

    @Column(nullable = false)
    private String nombre;
    private Integer codigoPostal;
}
