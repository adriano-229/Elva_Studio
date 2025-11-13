package com.projects.mycar.mycar_admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projects.mycar.mycar_admin.domain.enums.TipoDocumentacion;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DocumentacionDTO extends BaseDTO {

    @NotNull(message = "El tipo de documentación es obligatorio")
    private TipoDocumentacion tipoDocumentacion;

    // Puede ser null porque se define al guardar el archivo en el backend
    private String pathArchivo;

    @Size(max = 255, message = "La observación no puede tener más de 255 caracteres")
    private String observacion;

    //puede ser null - se define al guardar el archivo 
    /*@Size(min = 3, max = 100, message = "El nombre del archivo debe tener entre 3 y 100 caracteres")
    @Pattern(regexp = ".*\\.(pdf|docx|doc)$", message = "El archivo debe tener una extensión válida (.pdf, .docx, .doc)")*/
    private String nombreArchivo;

    @JsonIgnore
    private MultipartFile pdf;

}
