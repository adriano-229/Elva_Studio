package com.example.mycar.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@PrimaryKeyJoinColumn(name = "persona_id")
@Getter
@Setter
@NoArgsConstructor
public class Cliente extends Persona {

    @Column(name = "direccion_estadia", nullable = false)
    private String direccionEstadia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nacionalidad_id")
    private Nacionalidad nacionalidad;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alquiler_id")
    private Alquiler alquiler;*/
}
