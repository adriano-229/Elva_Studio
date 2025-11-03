package com.projects.gym.gym_app.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "promocion")
@PrimaryKeyJoinColumn(name = "mensaje_id") 
public class Promocion extends Mensaje {

    @Column(name = "fecha_envio_promocion")
    private LocalDate fechaEnvioPromocion;

    @Column(name = "cantidad_socios_enviados")
    private Long cantidadSociosEnviados = 0L;
}
