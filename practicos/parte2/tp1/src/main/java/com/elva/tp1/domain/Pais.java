package com.elva.tp1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pais")
@Data
public class Pais extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String nombre;
}
