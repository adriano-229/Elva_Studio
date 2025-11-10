package com.example.mycar.entities;

import com.example.mycar.enums.TipoDocumentacion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "documentacion")
public class Documentacion extends Base {

    @Column(name = "tipo_documentacion", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDocumentacion tipoDocumentacion;

    @Column(name = "path_archivo", nullable = true)
    private String pathArchivo;

    @Column(name = "observacion", nullable = false)
    private String observacion;

    @Column(name = "nombre_archivo", nullable = true)
    private String nombreArchivo;

}
