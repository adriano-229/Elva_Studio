package com.projects.mycar.mycar_server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pais")
public class Pais extends Base {

    @Column(nullable = false, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "pais", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Provincia> provincias = new HashSet<>();
}
