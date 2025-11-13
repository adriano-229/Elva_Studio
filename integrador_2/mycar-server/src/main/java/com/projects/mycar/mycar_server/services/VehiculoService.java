package com.projects.mycar.mycar_server.services;

import com.projects.mycar.mycar_server.dto.VehiculoDTO;
import com.projects.mycar.mycar_server.enums.EstadoVehiculo;

import java.time.LocalDate;
import java.util.List;

public interface VehiculoService extends BaseService<VehiculoDTO, Long> {
    List<VehiculoDTO> buscarVehiculosPorEstadoYPeriodo(EstadoVehiculo estado,
                                                       LocalDate fechaDesde,
                                                       LocalDate fechaHasta);

    List<VehiculoDTO> buscarPorEstado(EstadoVehiculo estado);
}
