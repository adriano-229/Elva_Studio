package com.projects.mycar.mycar_server.entities;

import com.projects.mycar.mycar_server.enums.TipoContacto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "contacto")
@Getter
@Setter
@NoArgsConstructor
public class Contacto extends Base {

    @Column(name = "tipoContacto", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoContacto tipoContacto;

    @Column(name = "observacion", nullable = false)
    private String observacion;


}
