package com.elva.tp1.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pais")
@Data
public class Pais extends BaseActivable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nombre;
}
