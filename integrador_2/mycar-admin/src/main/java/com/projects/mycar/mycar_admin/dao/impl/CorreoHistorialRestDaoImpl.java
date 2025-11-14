package com.projects.mycar.mycar_admin.dao.impl;

import com.projects.mycar.mycar_admin.dao.CorreoHistorialRestDao;
import com.projects.mycar.mycar_admin.domain.CorreoHistorialDTO;
import com.projects.mycar.mycar_admin.domain.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@Repository
public class CorreoHistorialRestDaoImpl implements CorreoHistorialRestDao {

    private static final String BASE_URL = "http://localhost:8083/api/correos/historial";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public PageResponse<CorreoHistorialDTO> buscar(String destinatario,
                                                   Boolean exito,
                                                   LocalDate fechaDesde,
                                                   LocalDate fechaHasta,
                                                   int page,
                                                   int size) throws Exception {

        try {
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromUriString(BASE_URL)
                    .queryParam("page", page)
                    .queryParam("size", size);

            if (destinatario != null && !destinatario.isBlank()) {
                builder.queryParam("destinatario", destinatario);
            }
            if (exito != null) {
                builder.queryParam("exito", exito);
            }
            if (fechaDesde != null) {
                builder.queryParam("fechaDesde", fechaDesde);
            }
            if (fechaHasta != null) {
                builder.queryParam("fechaHasta", fechaHasta);
            }

            ResponseEntity<PageResponse<CorreoHistorialDTO>> response = restTemplate.exchange(
                    builder.build(true).toUri(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );

            return response.getBody();

        } catch (HttpClientErrorException e) {
            throw new Exception(e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new Exception("Error al obtener historial de correos", e);
        }
    }
}
