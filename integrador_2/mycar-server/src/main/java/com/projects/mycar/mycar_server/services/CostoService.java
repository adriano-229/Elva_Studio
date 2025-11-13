package com.projects.mycar.mycar_server.services;

import com.projects.mycar.mycar_server.dto.AlquilerFormDTO;
import com.projects.mycar.mycar_server.dto.PagareDTO;

import java.util.List;

public interface CostoService {
    PagareDTO calcularCostosYGenerarPagare(List<Long> alquilerIds, Long clienteId) throws Exception;

    AlquilerFormDTO calcularCostoAlquiler(AlquilerFormDTO alquiler) throws Exception;

    //Double calcularCostoAlquiler(Long alquilerId) throws Exception;
}
