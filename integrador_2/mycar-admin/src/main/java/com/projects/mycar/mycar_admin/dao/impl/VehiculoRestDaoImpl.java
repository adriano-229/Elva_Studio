package com.projects.mycar.mycar_admin.dao.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.util.UriComponentsBuilder;

import com.projects.mycar.mycar_admin.dao.VehiculoRestDao;
import com.projects.mycar.mycar_admin.domain.VehiculoDTO;
import com.projects.mycar.mycar_admin.domain.enums.EstadoVehiculo;

@Repository
public class VehiculoRestDaoImpl extends BaseRestDaoImpl<VehiculoDTO, Long> implements VehiculoRestDao {

    public VehiculoRestDaoImpl() {
        super(VehiculoDTO.class, VehiculoDTO[].class, "/api/v1/vehiculos");
    }

    @Override
    public List<VehiculoDTO> buscarPorEstadoYPeriodo(EstadoVehiculo estado, LocalDate desde, LocalDate hasta)
            throws Exception {

        try {
            String uri = UriComponentsBuilder
                    .fromUriString(baseUrl + "/searchByEstadoYPeriodo")
                    .queryParam("estado", estado)
                    .queryParam("desde", desde)
                    .queryParam("hasta", hasta)
                    .build()
                    .toUriString();

            ResponseEntity<VehiculoDTO[]> response = restTemplate.getForEntity(uri, entityArrayClass);
            VehiculoDTO[] array = response.getBody();

            if (array == null) {
                return new ArrayList<>();
            }

            return Arrays.asList(array);

        } catch (Exception e) {
            throw new Exception("Error al buscar vehiculo por estado y periodo", e);
        }
    }

    @Override
    public List<VehiculoDTO> buscarPorEstado(EstadoVehiculo estado) throws Exception {

        try {
            String uri = UriComponentsBuilder
                    .fromUriString(baseUrl + "/searchByEstado")
                    .queryParam("estado", estado)
                    .build()
                    .toUriString();

            ResponseEntity<VehiculoDTO[]> response = restTemplate.getForEntity(uri, entityArrayClass);
            VehiculoDTO[] array = response.getBody();

            if (array == null) {
                return new ArrayList<>();
            }

            return Arrays.asList(array);

        } catch (Exception e) {
            throw new Exception("Error al buscar vehiculo por estado", e);
        }
    }
}
