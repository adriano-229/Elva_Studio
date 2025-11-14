package com.projects.mycar.mycar_admin.dao.impl;

import com.projects.mycar.mycar_admin.domain.RespuestaPagoDTO;
import com.projects.mycar.mycar_admin.domain.SolicitudPagoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class RespuestaPagoRestDaoImpl {

    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl = "http://168.181.186.171:8083/api/pagos";

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
