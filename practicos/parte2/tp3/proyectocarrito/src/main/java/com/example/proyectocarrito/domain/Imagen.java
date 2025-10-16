package com.example.proyectocarrito.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Imagen {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nombre;
    private String mime;
    @Lob
    private byte[] contenido;
    private boolean eliminado = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private Articulo articulo;
}
