package com.projects.mycar.mycar_server.controller;

import com.projects.mycar.mycar_server.dto.correos.RegistroEnvioCorreoDTO;
import com.projects.mycar.mycar_server.services.RegistroEnvioCorreoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/correos")
@RequiredArgsConstructor
@Tag(name = "Correos", description = "Consulta del historial de envíos de correo.")
public class CorreosController {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_DATE;
    private final RegistroEnvioCorreoService registroEnvioCorreoService;

    @GetMapping("/historial")
    @Operation(summary = "Obtiene el historial de envíos de correo",
            description = "Permite filtrar por destinatario (contains) y por estado de envío.")
    public ResponseEntity<Page<RegistroEnvioCorreoDTO>> obtenerHistorial(
            @RequestParam(required = false) String destinatario,
            @RequestParam(required = false) Boolean exito,
            @RequestParam(required = false) String fechaDesde,
            @RequestParam(required = false) String fechaHasta,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "fechaEnvio"));
        LocalDateTime desde = parseFecha(fechaDesde, true);
        LocalDateTime hasta = parseFecha(fechaHasta, false);
        Page<RegistroEnvioCorreoDTO> historial = registroEnvioCorreoService
                .obtenerHistorial(destinatario, exito, desde, hasta, pageable);
        return ResponseEntity.ok(historial);
    }

    private LocalDateTime parseFecha(String fecha, boolean inicioDia) {
        if (!StringUtils.hasText(fecha)) {
            return null;
        }
        try {
            LocalDate date = LocalDate.parse(fecha, DATE_FORMAT);
            return inicioDia ? date.atStartOfDay() : date.atTime(LocalTime.MAX);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use YYYY-MM-DD");
        }
    }
}
