package com.example.mycar.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Provincia extends Base {

    @Column(name = "nombre", nullable = false)
    private String nombre;

    // leave default EAGER, usually acceptable since you often need the Pais when working with a Provincia, as one join is cheap
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Pais pais;

    // same logic as in Pais -> Provincia, but now: Provincia -> Departamento
    @OneToMany(mappedBy = "provincia", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Departamento> departamentos = new HashSet<>();

}