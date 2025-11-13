package com.projects.mycar.mycar_server.entities;

import com.projects.mycar.mycar_server.enums.TipoTelefono;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@PrimaryKeyJoinColumn(name = "contacto_id")
@Getter
@Setter
@NoArgsConstructor
public class ContactoTelefonico extends Contacto {

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "tipo_telefono", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoTelefono tipoTelefono;
}
