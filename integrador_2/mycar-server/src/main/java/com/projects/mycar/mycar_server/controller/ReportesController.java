package com.projects.mycar.mycar_server.controller;

import com.projects.mycar.mycar_server.enums.ReportFormat;
import com.projects.mycar.mycar_server.services.ReporteService;
import com.projects.mycar.mycar_server.dto.reportes.AlquilerReporteDTO;
import com.projects.mycar.mycar_server.dto.reportes.RecaudacionModeloDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
@Tag(name = "Reportes", description = "Endpoints para generación y descarga de reportes administrativos.")
public class ReportesController {

    private final ReporteService reporteService;

    @GetMapping("/alquileres")
    @Operation(
            summary = "Listado de alquileres por período",
            description = """
                    Devuelve el detalle de alquileres dentro del rango indicado. \
                    Si se especifica `formato`, responde con un archivo PDF o XLSX listo para descargar.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reporte generado correctamente",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = AlquilerReporteDTO.class)),
                                    @Content(mediaType = "application/pdf"),
                                    @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                            }),
                    @ApiResponse(responseCode = "400", description = "Parámetros inválidos", content = @Content),
                    @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Acceso denegado", content = @Content)
            }
    )
    public ResponseEntity<?> obtenerAlquileresPorPeriodo(
            @Parameter(description = "Fecha inicial del rango (formato YYYY-MM-DD).", required = true, example = "2024-01-01")
            @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @Parameter(description = "Fecha final del rango (formato YYYY-MM-DD).", required = true, example = "2024-12-31")
            @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @Parameter(description = "Formato opcional de exportación. Valores admitidos: pdf, xlsx.", example = "pdf")
            @RequestParam(value = "formato", required = false) String formato) {

        //Validaciones
        validacionesFechasRequest(desde, hasta);
        //Verificar si recibimos formato
        if (!StringUtils.hasText(formato)) {
            return ResponseEntity.ok(reporteService.obtenerAlquileresPorPeriodo(desde, hasta));
        }

        ReportFormat reportFormat;
        try {
            reportFormat = ReportFormat.fromQueryValue(formato);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(BAD_REQUEST, ex.getMessage(), ex);
        }

        byte[] archivo = reporteService.exportarAlquileresPorPeriodo(desde, hasta, reportFormat);
        Resource resource = new ByteArrayResource(archivo);
        String filename = String.format("alquileres_%s_%s%s", desde, hasta, reportFormat.filenameSuffix());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(reportFormat.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

    @GetMapping("/recaudacion-modelo")
    @Operation(
            summary = "Recaudación agrupada por modelo de vehículo",
            description = """
                    Consolida el total recaudado, cantidad de alquileres y flota disponible por modelo / marca \
                    en el periodo de facturación indicado. Se puede descargar en PDF o XLSX.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reporte generado correctamente",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = RecaudacionModeloDTO.class)),
                                    @Content(mediaType = "application/pdf"),
                                    @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                            }),
                    @ApiResponse(responseCode = "400", description = "Parámetros inválidos", content = @Content),
                    @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Acceso denegado", content = @Content)
            }
    )
    public ResponseEntity<?> obtenerRecaudacionPorModelo(
            @Parameter(description = "Fecha inicial de facturación (formato YYYY-MM-DD).", required = true, example = "2024-01-01")
            @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @Parameter(description = "Fecha final de facturación (formato YYYY-MM-DD).", required = true, example = "2024-12-31")
            @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @Parameter(description = "Formato opcional de exportación. Valores admitidos: pdf, xlsx.", example = "xlsx")
            @RequestParam(value = "formato", required = false) String formato) {

        //Validaciones
        validacionesFechasRequest(desde, hasta);
        //Verificar si recibimos formato
        if (!StringUtils.hasText(formato)) {
            return ResponseEntity.ok(reporteService.obtenerRecaudacionPorModelo(desde, hasta));
        }

        ReportFormat reportFormat;
        try {
            reportFormat = ReportFormat.fromQueryValue(formato);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(BAD_REQUEST, ex.getMessage(), ex);
        }

        byte[] archivo = reporteService.exportarRecaudacionPorModelo(desde, hasta, reportFormat);
        Resource resource = new ByteArrayResource(archivo);
        String filename = String.format("recaudacion_modelo_%s_%s%s", desde, hasta, reportFormat.filenameSuffix());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(reportFormat.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }


    private void validacionesFechasRequest(LocalDate desde, LocalDate hasta) {
        if (desde == null || hasta == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Los parámetros 'desde' y 'hasta' son obligatorios.");
        }

        if (hasta.isBefore(desde)) {
            throw new ResponseStatusException(BAD_REQUEST, "El parámetro 'hasta' debe ser mayor o igual a 'desde'.");
        }
    }
}
