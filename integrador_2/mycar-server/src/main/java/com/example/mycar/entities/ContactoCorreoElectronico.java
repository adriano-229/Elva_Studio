package com.example.mycar.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@PrimaryKeyJoinColumn(name = "contacto_id")
@Getter
@Setter
@NoArgsConstructor
public class ContactoCorreoElectronico extends Contacto {

    @Column(name = "email", nullable = false)
    private String email;
}
