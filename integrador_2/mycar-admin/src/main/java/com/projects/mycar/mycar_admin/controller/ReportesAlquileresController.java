package com.projects.mycar.mycar_admin.controller;

import com.projects.mycar.mycar_admin.domain.ReportFileDTO;
import com.projects.mycar.mycar_admin.domain.ReporteAlquilerDTO;
import com.projects.mycar.mycar_admin.service.impl.ReportesAlquileresServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reportes/alquileres")
public class ReportesAlquileresController extends BaseControllerImpl<ReporteAlquilerDTO, ReportesAlquileresServiceImpl> {

    private static final String VIEW = "view/reportes/alquileres/reporte";
    private static final String REDIRECT = "redirect:/reportes/alquileres";

    @Override
    protected String getViewList() {
        return VIEW;
    }

    @Override
    protected String getViewEdit() {
        return VIEW;
    }

    @Override
    protected String getRedirectList() {
        return REDIRECT;
    }

    @Override
    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("msgError", "Los reportes solo están disponibles para consulta.");
        return VIEW;
    }

    @GetMapping(params = "!formato")
    public String verReporte(
            @RequestParam(value = "desde", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(value = "hasta", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            Model model) {

        LocalDate fechaDesde = desde != null ? desde : LocalDate.now().withDayOfMonth(1);
        LocalDate fechaHasta = hasta != null ? hasta : LocalDate.now();

        model.addAttribute("fechaDesde", fechaDesde);
        model.addAttribute("fechaHasta", fechaHasta);

        boolean filtrosAplicados = desde != null && hasta != null;
        model.addAttribute("filtrosAplicados", filtrosAplicados);

        List<ReporteAlquilerDTO> resultados = new ArrayList<>();
        String mensajeError = null;

        if (filtrosAplicados) {
            try {
                resultados = servicio.obtenerAlquileres(desde, hasta);
            } catch (Exception e) {
                mensajeError = e.getMessage();
                model.addAttribute("msgError", mensajeError);
            }
        }

        model.addAttribute("entities", resultados);
        model.addAttribute("mostrarResultados", filtrosAplicados && mensajeError == null);
        model.addAttribute("totalAlquileres", resultados.size());
        model.addAttribute("totalMonto", resultados.stream()
                .map(ReporteAlquilerDTO::getMontoTotal)
                .filter(value -> value != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        return VIEW;
    }

    @GetMapping(params = "formato")
    public ResponseEntity<byte[]> descargarReporte(
            @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @RequestParam("formato") String formato) {
        try {
            ReportFileDTO archivo = servicio.descargarAlquileres(desde, hasta, formato);
            MediaType mediaType = MediaType.parseMediaType(archivo.getContentType());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + archivo.getNombreArchivo() + "\"")
                    .contentType(mediaType)
                    .contentLength(archivo.getContenido().length)
                    .body(archivo.getContenido());

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
