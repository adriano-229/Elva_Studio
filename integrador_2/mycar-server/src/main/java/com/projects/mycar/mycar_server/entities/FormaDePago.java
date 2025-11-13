package com.projects.mycar.mycar_server.entities;


import com.projects.mycar.mycar_server.enums.TipoPago;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "forma_de_pago")
public class FormaDePago extends Base {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pago", nullable = false, length = 30)
    private TipoPago tipoPago;

    @Column(length = 255)
    private String observacion;

}