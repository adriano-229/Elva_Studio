package com.example.mycar.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
    private List<Localidad> localidades;

}
