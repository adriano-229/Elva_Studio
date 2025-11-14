package com.example.mycar.controller;

import com.example.mycar.dto.DocumentacionDTO;
import com.example.mycar.entities.Documentacion;
import com.example.mycar.services.impl.DocumentacionServiceImpl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/documentacion")
public class DocumentacionController extends BaseControllerImpl<Documentacion, DocumentacionDTO, DocumentacionServiceImpl> {
	
	@Value("${app.upload.docs.dir}")
    private String uploadDir;
	
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
            
            String nombreArchivo = servicio.almacenarPdf(archivo);
            documentacion.setNombreArchivo(nombreArchivo);
            documentacion.setPathArchivo(uploadDir + File.separator + nombreArchivo);
                        
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
        	
            return ResponseEntity.status(HttpStatus.OK).body(servicio.updateDocumentacion(id, documentacion, archivo));

        } catch (Exception e) {
            log.error("Error actualizando documentación", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. por favor intente más tarde.\"}");
        }

    }
    
    @GetMapping("/test")
    public String test() {
        return "Servidor funcionando correctamente ✅";
    }
    
    
    @GetMapping("/ver-pdf/{nombreArchivo:.+}")
    @ResponseBody
    public ResponseEntity<Resource> verPdf(@PathVariable String nombreArchivo) {
        try {
        	
        	File f = new File("C:\\MyCar\\Documentacion\\1763058951573_libro-8399525278276354185.pdf");
        	System.out.println("Directorio actual del proceso: " + System.getProperty("user.dir"));
        	System.out.println("Sistema operativo: " + System.getProperty("os.name"));
        	System.out.println("Archivo existe? " + new File("C:/MyCar/Documentacion/1763058951573_libro-8399525278276354185.pdf").exists());
        	System.out.println("Legible (File.canRead)? " + f.canRead());
        	System.out.println("Es archivo? " + f.isFile());
        	
        	
            Path rutaArchivo = Paths.get(uploadDir).resolve(nombreArchivo);
            System.out.println("Buscando archivo: " + rutaArchivo);

            if (!Files.exists(rutaArchivo)) {
                System.out.println("Archivo no encontrado: " + rutaArchivo);
                return ResponseEntity.notFound().build();
            }

            Resource recurso = new FileSystemResource(rutaArchivo.toFile());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + recurso.getFilename() + "\"")
                    .body(recurso);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    
    

    

}
