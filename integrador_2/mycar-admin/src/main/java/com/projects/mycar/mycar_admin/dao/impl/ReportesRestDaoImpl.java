package com.projects.mycar.mycar_admin.dao.impl;

import com.projects.mycar.mycar_admin.dao.ReportesRestDao;
import com.projects.mycar.mycar_admin.domain.ReportFileDTO;
import com.projects.mycar.mycar_admin.domain.ReporteRecaudacionModeloDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class ReportesRestDaoImpl implements ReportesRestDao {

    private final String baseUrl;

    private final RestTemplate restTemplate;

    public ReportesRestDaoImpl(RestTemplate restTemplate,
                               @Value("${mycar.api.base-url}") String apiBaseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = UriComponentsBuilder.fromHttpUrl(apiBaseUrl)
                .path("/reportes")
                .toUriString();
    }

    @Override
    public List<ReporteRecaudacionModeloDTO> obtenerRecaudacionPorModelo(LocalDate desde, LocalDate hasta)
            throws Exception {

        try {
            String uri = UriComponentsBuilder
                    .fromUriString(baseUrl + "/recaudacion-modelo")
                    .queryParam("desde", desde)
                    .queryParam("hasta", hasta)
                    .build()
                    .toUriString();

            ResponseEntity<ReporteRecaudacionModeloDTO[]> response =
                    restTemplate.getForEntity(uri, ReporteRecaudacionModeloDTO[].class);

            ReporteRecaudacionModeloDTO[] array = response.getBody();

            if (array == null) {
                return new ArrayList<>();
            }

            return Arrays.asList(array);

        } catch (HttpClientErrorException e) {
            throw new Exception(e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new Exception("Error al obtener la recaudación agrupada por modelo", e);
        }
    }

    @Override
    public ReportFileDTO descargarRecaudacionPorModelo(LocalDate desde, LocalDate hasta, String formato)
            throws Exception {
        try {
            URI uri = UriComponentsBuilder
                    .fromUriString(baseUrl + "/recaudacion-modelo")
                    .queryParam("desde", desde)
                    .queryParam("hasta", hasta)
                    .queryParam("formato", formato)
                    .build()
                    .toUri();

            MediaType acceptType = resolveMediaType(formato);

            RequestEntity<Void> request = RequestEntity
                    .get(uri)
                    .accept(acceptType)
                    .build();

            ResponseEntity<byte[]> response = restTemplate.exchange(request, byte[].class);
            byte[] contenido = response.getBody();

            if (contenido == null || contenido.length == 0) {
                throw new Exception("El reporte no devolvió contenido para descargar");
            }

            MediaType responseType = response.getHeaders().getContentType();
            String nombreArchivo = extraerNombreArchivo(
                    response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION),
                    formato
            );

            return ReportFileDTO.builder()
                    .contenido(contenido)
                    .contentType(responseType != null ? responseType.toString() : acceptType.toString())
                    .nombreArchivo(nombreArchivo)
                    .build();

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (HttpClientErrorException e) {
            throw new Exception(e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new Exception("Error al descargar el reporte de recaudación por modelo", e);
        }
    }

    private MediaType resolveMediaType(String formato) {
        if (formato == null) {
            throw new IllegalArgumentException("Debe indicar el formato del archivo");
        }
        if ("pdf".equalsIgnoreCase(formato)) {
            return MediaType.APPLICATION_PDF;
        }
        if ("xlsx".equalsIgnoreCase(formato)) {
            return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        }
        throw new IllegalArgumentException("Formato no soportado: " + formato);
    }

    private String extraerNombreArchivo(String contentDispositionHeader, String formato) {
        String nombrePorDefecto = "recaudacion-modelo." + formato.toLowerCase();

        if (contentDispositionHeader == null || contentDispositionHeader.isBlank()) {
            return nombrePorDefecto;
        }

        try {
            ContentDisposition disposition = ContentDisposition.parse(contentDispositionHeader);
            if (disposition.getFilename() != null && !disposition.getFilename().isBlank()) {
                return disposition.getFilename();
            }
        } catch (Exception ignored) {
            // Si el header no tiene el formato esperado, devolvemos el nombre por defecto
        }

        return nombrePorDefecto;
    }

    @Override
    public void crear(ReporteRecaudacionModeloDTO dto) throws Exception {
        throw new UnsupportedOperationException("Operación no soportada para reportes");
    }

    @Override
    public void actualizar(ReporteRecaudacionModeloDTO dto) throws Exception {
        throw new UnsupportedOperationException("Operación no soportada para reportes");
    }

    @Override
    public void eliminar(Long id) throws Exception {
        throw new UnsupportedOperationException("Operación no soportada para reportes");
    }

    @Override
    public boolean existsById(Long id) throws Exception {
        return false;
    }

    @Override
    public Optional<ReporteRecaudacionModeloDTO> buscarPorId(Long id) throws Exception {
        return Optional.empty();
    }

    @Override
    public List<ReporteRecaudacionModeloDTO> listarActivo() throws Exception {
        return new ArrayList<>();
    }

}
