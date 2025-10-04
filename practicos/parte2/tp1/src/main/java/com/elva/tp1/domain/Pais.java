package com.elva.tp1.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Pais extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "pais", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Provincia> provincias = new ArrayList<>();
}

