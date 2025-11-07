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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
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

    @ManyToOne(optional=false)
    @JoinColumn(name="departamento_id", nullable=false)
    private Departamento departamento;
    
    @OneToMany(mappedBy = "localidad", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Direccion> direcciones;

}