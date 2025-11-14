package com.projects.mycar.mycar_admin.dao.impl;

import com.projects.mycar.mycar_admin.dao.CostoRestDao;
import com.projects.mycar.mycar_admin.domain.AlquilerFormDTO;
import com.projects.mycar.mycar_admin.domain.PagareDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class CostoRestDaoImpl implements CostoRestDao {

    @Autowired
    private RestTemplate restTemplate;

    private final String baseUrl = "http://localhost:8083/api/costos";

    @Override
    public PagareDTO calcularPagare(List<Long> alquilerIds, Long clienteId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AlquilerFormDTO calcularCostoAlquiler(AlquilerFormDTO alquiler) throws Exception {

        try {

            String uri = baseUrl + "/calcularCosto";

            ResponseEntity<AlquilerFormDTO> response = restTemplate.postForEntity(
                    uri,
                    alquiler,
                    AlquilerFormDTO.class
            );

            return response.getBody();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al cotizar alquiler");
        }

    }

}
