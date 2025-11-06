package com.example.proyectocarrito.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Detalle {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private boolean eliminado = false;

    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    @ManyToOne(fetch = FetchType.LAZY)
    private Carrito carrito;

    @ManyToOne(fetch = FetchType.LAZY)
    private Articulo articulo;
}
