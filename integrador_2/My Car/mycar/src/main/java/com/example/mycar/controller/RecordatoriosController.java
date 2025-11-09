package com.example.mycar.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mycar.dto.recordatorios.RecordatorioJobResponse;
import com.example.mycar.services.RecordatorioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/jobs/recordatorios")
@Tag(name = "Recordatorios", description = "Jobs internos para envío de recordatorios automáticos.")
@RequiredArgsConstructor
public class RecordatoriosController {

    private final RecordatorioService recordatorioService;
    @PostMapping("/prueba")
    @Operation(
            summary = "Envía un correo de prueba a un cliente",
            description = "Correo sencillo para validar la configuración SMTP y la casilla del cliente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Correo enviado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Acceso denegado", content = @Content)
            }
    )
    public ResponseEntity<Void> enviarPrueba(
            @Parameter(description = "ID del cliente que recibirá el mensaje de prueba.", required = true)
            @RequestParam("clienteId") Long clienteId) {
        recordatorioService.enviarPrueba(clienteId);
        return ResponseEntity.ok().build();
    }
}
