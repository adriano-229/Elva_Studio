package com.elva.tp1.controller;

import com.elva.tp1.service.ExportService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/export")
public class ExportController {

    private final ExportService exportService;

    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @GetMapping("/proveedores/pdf")
    public ResponseEntity<byte[]> descargarPdfProveedores() {
        try {
            byte[] data = exportService.generarPdfProveedores();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                    ContentDisposition.builder("attachment")
                            .filename("proveedores.pdf")
                            .build());
            headers.setContentLength(data.length);
            return new ResponseEntity<>(data, headers, HttpStatus.OK);
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generando PDF: " + ex.getMessage()).getBytes(StandardCharsets.UTF_8));
        }
    }

    @GetMapping("/empresas/excel")
    public ResponseEntity<byte[]> descargarExcelEmpresas() {
        try {
            byte[] data = exportService.generarExcelEmpresas();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDisposition(
                    ContentDisposition.builder("attachment")
                            .filename("empresas.xlsx")
                            .build());
            headers.setContentLength(data.length);
            return new ResponseEntity<>(data, headers, HttpStatus.OK);
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generando Excel: " + ex.getMessage()).getBytes(StandardCharsets.UTF_8));
        }
    }
}
