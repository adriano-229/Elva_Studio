package com.example.mycar.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "direccion")
public class Direccion extends Base {

    @Column(name = "calle", nullable = false)
    private String calle;

    @Column(name = "numeracion", nullable = false)
    private Integer numeracion;

    @Column(name = "barrio", nullable = false)
    private String barrio;

    @Column(name = "manzana_piso", nullable = false)
    private String manzana_piso;

    @Column(name = "casa_departamento", nullable = false)
    private String casa_departamento;

    @Column(name = "referencia", nullable = false)
    private String referencia;

    @ManyToOne(optional=false)
    @JoinColumn(name="localidad_id", nullable = false)
    private Localidad localidad;
    
}	
