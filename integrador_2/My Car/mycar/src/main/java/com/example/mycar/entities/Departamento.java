package com.example.mycar.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "departamento")
public class Departamento extends Base {

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Provincia provincia;

    @OneToMany(mappedBy = "departamento", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Localidad> localidades = new HashSet<>();

}
