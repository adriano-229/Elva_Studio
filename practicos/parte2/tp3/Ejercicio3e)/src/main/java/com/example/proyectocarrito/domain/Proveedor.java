package com.example.proyectocarrito.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Proveedor {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nombre;
    private boolean eliminado = false;

    // Ejercicio 3)f
    private String direccion;     // p.ej. "Av. Siempre Viva 742, Salta, AR"
    private Double latitud;       // -24.7821
    private Double longitud;      // -65.4238

    @OneToMany(mappedBy = "proveedor")
    private List<Articulo> articulos = new ArrayList<>();
}