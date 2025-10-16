package com.example.proyectocarrito.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nombre;
    private String clave;
    private boolean eliminado = false;

    @OneToMany(mappedBy = "usuario")
    private List<Carrito> carritos = new ArrayList<>();
}
