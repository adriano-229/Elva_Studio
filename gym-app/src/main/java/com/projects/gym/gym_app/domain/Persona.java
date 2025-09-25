package com.projects.gym.gym_app.domain;

import java.time.LocalDate;

import com.projects.gym.gym_app.domain.enums.TipoDocumento;

import jakarta.persistence.*;
import lombok.*;


@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public class Persona {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String nombre;
    @Column(nullable = false) private String apellido;

    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    private String numeroDocumento;
    private String telefono;
    private String correoElectronico;

    private boolean eliminado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;
}
