package com.elva.tp1.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Empresa extends BaseEntity {

    @Column(nullable = false)
    private String razonSocial;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Direccion direccion;
}
