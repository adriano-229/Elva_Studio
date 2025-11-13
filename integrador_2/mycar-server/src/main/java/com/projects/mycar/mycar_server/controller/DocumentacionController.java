package com.projects.mycar.mycar_server.controller;

import com.projects.mycar.mycar_server.dto.DocumentacionDTO;
import com.projects.mycar.mycar_server.entities.Documentacion;
import com.projects.mycar.mycar_server.services.impl.DocumentacionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/documentacion")
public class DocumentacionController extends BaseControllerImpl<Documentacion, DocumentacionDTO, DocumentacionServiceImpl> {

    private static final Logger log = LoggerFactory.getLogger(DocumentacionController.class);

    @PostMapping(value = "saveConDocumento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> save(
            @RequestPart("documentacion") DocumentacionDTO documentacion,
            @RequestPart("pdf") MultipartFile archivo) {

        try {
            if (archivo == null || archivo.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\":\"El PDF es requerido\"}");
            }

            String ruta = servicio.almacenarPdf(archivo);
            documentacion.setPathArchivo(ruta);

            return ResponseEntity.status(HttpStatus.OK).body(servicio.save(documentacion));

        } catch (Exception e) {
            log.error("Error guardando documentación", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. por favor intente más tarde.\"}");
        }

    }

    @PutMapping(value = "updateConDocumento/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestPart DocumentacionDTO documentacion,
            @RequestPart(value = "pdf", required = false) MultipartFile archivo) {

        try {

            return ResponseEntity.status(HttpStatus.OK).body(servicio.update(id, documentacion, archivo));

        } catch (Exception e) {
            log.error("Error actualizando documentación", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. por favor intente más tarde.\"}");
        }

    }


}
