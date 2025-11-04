package com.example.mecanico_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "clientes")
public class Cliente extends Persona {

    @Column(nullable = false, unique = true)
    private String documento;

    public Cliente(String nombre, String apellido, String documento) {
        super(nombre, apellido);
        this.documento = documento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
