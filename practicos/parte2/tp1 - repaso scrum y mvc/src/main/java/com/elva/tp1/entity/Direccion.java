package com.elva.tp1.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "direccion")
@Data
public class Direccion extends BaseActivable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_localidad", nullable = false)
    private Localidad localidad;

    private String calle;
    private Integer altura;
}
