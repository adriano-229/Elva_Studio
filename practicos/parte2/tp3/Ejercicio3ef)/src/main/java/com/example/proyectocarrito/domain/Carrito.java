package com.example.proyectocarrito.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Carrito {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private double total = 0d;
    private boolean eliminado = false;
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    // Composición: el detalle no vive sin el carrito
    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Detalle> detalles = new ArrayList<>();
    // getters/setters
}
