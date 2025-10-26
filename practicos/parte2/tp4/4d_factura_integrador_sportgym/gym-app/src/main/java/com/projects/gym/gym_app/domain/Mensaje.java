package com.projects.gym.gym_app.domain;

import com.projects.gym.gym_app.domain.enums.TipoMensaje;
import jakarta.persistence.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "mensaje")
@Inheritance(strategy = InheritanceType.JOINED) // una tabla por clase hija
public class Mensaje {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Lob
    @Column(nullable = false)
    private String texto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_mensaje", nullable = false, length = 20)
    private TipoMensaje tipoMensaje;

    @Column(nullable = false)
    private boolean eliminado = false;

    @Version
    private Long version;

    // Autor / remitente
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
