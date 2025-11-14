package com.projects.mycar.mycar_admin.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportFileDTO {

    private byte[] contenido;
    private String contentType;
    private String nombreArchivo;

}
