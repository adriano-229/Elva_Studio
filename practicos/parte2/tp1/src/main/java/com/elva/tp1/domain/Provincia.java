package com.elva.tp1.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Provincia extends BaseEntity {

    @Column(nullable = false)
    private String nombre;

    // leave default EAGER, usually acceptable since you often need the Pais when working with a Provincia, as one join is cheap
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Pais pais;

    // same logic as in Pais -> Provincia, but now: Provincia -> Departamento
    @OneToMany(mappedBy = "provincia", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Departamento> departamentos;
}
