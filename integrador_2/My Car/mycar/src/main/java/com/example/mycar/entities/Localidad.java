package com.example.mycar.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "localidad")
public class Localidad extends Base {

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "codigo_postal", nullable = false)
    private String codigoPostal;

    @ManyToOne
    private Departamento departamento;

}