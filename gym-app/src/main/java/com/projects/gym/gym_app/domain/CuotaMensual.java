// src/main/java/com/projects/gym/gym_app/domain/CuotaMensual.java
package com.projects.gym.gym_app.domain;

import com.projects.gym.gym_app.domain.enums.EstadoCuota;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "cuota_mensual")
public class CuotaMensual {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // Si tu "mes" es Enum propio, cambialo a @Enumerated
    @Column(name = "mes", nullable = false, length = 20)
    private String mes;

    @Column(name = "anio", nullable = false)
    private Integer anio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoCuota estado = EstadoCuota.PENDIENTE;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    // Valor de la cuota (snapshot). Si usás ValorCuota, podés mantener ambos.
    @Column(precision = 12, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private boolean eliminado;

    @Version
    private Long version;

    // Relación con socio (1..* desde Socio)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_id", nullable = false)
    private Socio socio;

    // (Opcional) Si querés referenciar la tabla de valores vigentes:
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "valor_cuota_id")
    // private ValorCuota valorCuota;
}
