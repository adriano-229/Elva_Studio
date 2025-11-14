package com.projects.mycar.mycar_admin.dao.impl;

import com.projects.mycar.mycar_admin.domain.RespuestaPagoDTO;
import com.projects.mycar.mycar_admin.domain.SolicitudPagoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Repository
public class RespuestaPagoRestDaoImpl {

    private final RestTemplate restTemplate;

    private final String baseUrl;

    public RespuestaPagoRestDaoImpl(RestTemplate restTemplate,
                                    @Value("${mycar.api.base-url}") String apiBaseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = UriComponentsBuilder.fromHttpUrl(apiBaseUrl)
                .path("/api/pagos")
                .toUriString();
    }

    public RespuestaPagoDTO procesarPago(SolicitudPagoDTO solicitud) throws Exception {

        try {

            String uri = baseUrl + "/procesar";

            ResponseEntity<RespuestaPagoDTO> response = restTemplate.postForEntity(
                    uri,
                    solicitud,
                    RespuestaPagoDTO.class
            );

            return response.getBody();

        } catch (Exception e) {
            throw new Exception("Error al procesar pago", e);
        }
    }


}
