package com.projects.mycar.mycar_admin.dao;

import com.projects.mycar.mycar_admin.domain.AlquilerFormDTO;
import com.projects.mycar.mycar_admin.domain.PagareDTO;

import java.util.List;

public interface CostoRestDao {

    PagareDTO calcularPagare(List<Long> alquilerIds, Long clienteId) throws Exception;

    AlquilerFormDTO calcularCostoAlquiler(AlquilerFormDTO alquiler) throws Exception;

}
