package com.elva.tp1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "persona_id")
public class Usuario extends Persona {

    @Column(nullable = false, unique = true)
    private String cuenta;

    @Column(nullable = false)
    private String clave; // almacenaré encriptada (BCrypt) en capas superiores
}
