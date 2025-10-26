// src/main/java/com/projects/gym/gym_app/domain/CuotaMensual.java
package com.projects.gym.gym_app.domain;

import com.projects.gym.gym_app.domain.enums.EstadoCuota;
import com.projects.gym.gym_app.domain.enums.Mes;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "cuota_mensual")
public class CuotaMensual {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "mes", nullable = false, length = 20)
    private Mes mes;

    @Column(name = "anio", nullable = false)
    private Integer anio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoCuota estado = EstadoCuota.ADEUDADA;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    /*// Valor de la cuota (snapshot). Si usás ValorCuota, podés mantener ambos.
    @Column(precision = 12, scale = 2)
    private BigDecimal valor;*/

    @Column(nullable = false)
    private boolean eliminado;

    @Version
    private Long version;
    

    // Relación con socio (1..* desde Socio)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_id", nullable = false)
    private Socio socio;

    // (Opcional) Si querés referenciar la tabla de valores vigentes:
    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "valor_cuota_id")
    private ValorCuota valorCuota;
    
    public String getDescripcion() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Cuota ");

        if (mes != null) {
            sb.append(mes);
        }

        if (anio != null) {
            sb.append(" ").append(anio);
        }

       return sb.toString();
    }
}
