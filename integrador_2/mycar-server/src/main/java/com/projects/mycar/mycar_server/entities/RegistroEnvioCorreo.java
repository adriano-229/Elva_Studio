package com.projects.mycar.mycar_server.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "registro_envio_correo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroEnvioCorreo extends Base {

    @Column(name = "destinatario", nullable = false, length = 255)
    private String destinatario;

    @Column(name = "asunto", nullable = false, length = 255)
    private String asunto;

    @Lob
    @Column(name = "cuerpo", nullable = false, columnDefinition = "LONGTEXT")
    private String cuerpo;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio;

    @Column(name = "exito", nullable = false)
    private Boolean exito;

    @Column(name = "mensaje_error", length = 1000)
    private String mensajeError;
}
