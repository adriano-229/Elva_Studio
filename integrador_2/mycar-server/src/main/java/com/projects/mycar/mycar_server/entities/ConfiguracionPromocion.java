package com.projects.mycar.mycar_server.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "configuracion_promocion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfiguracionPromocion extends Base {

    @Column(name = "porcentaje_descuento", nullable = false)
    private Double porcentajeDescuento;

    @Column(name = "mensaje_promocion", nullable = false, length = 1000)
    private String mensajePromocion;

    @Column(name = "activa", nullable = false)
    private Boolean activa = true;
}

