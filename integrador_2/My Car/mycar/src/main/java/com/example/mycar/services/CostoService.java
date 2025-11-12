package com.example.mycar.services;

import com.example.mycar.dto.AlquilerFormDTO;

import com.example.mycar.dto.PagareDTO;

import java.util.List;

public interface CostoService {
    PagareDTO calcularCostosYGenerarPagare(List<Long> alquilerIds, Long clienteId) throws Exception;
    
    AlquilerFormDTO calcularCostoAlquiler(AlquilerFormDTO alquiler) throws Exception;
    
    //Double calcularCostoAlquiler(Long alquilerId) throws Exception;

}
