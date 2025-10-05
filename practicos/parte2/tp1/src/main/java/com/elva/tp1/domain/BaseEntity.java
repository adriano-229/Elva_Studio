package com.elva.tp1.domain;

import jakarta.persistence.*;
import lombok.Data;


@Data
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private boolean eliminado = false;
}
