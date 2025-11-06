package com.elva.tp1.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@PrimaryKeyJoinColumn
public class Usuario extends Persona {

    @Column(nullable = false, unique = true)
    private String cuenta;

    @Column(nullable = false)
    private String clave; // encriptación con BCrypt en capa de servicio

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol = Rol.USUARIO; // Por defecto es USUARIO

    public enum Rol {
        ADMIN, USUARIO
    }
}
