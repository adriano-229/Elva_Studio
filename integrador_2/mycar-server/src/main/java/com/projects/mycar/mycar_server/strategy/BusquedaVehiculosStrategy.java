package com.projects.mycar.mycar_server.strategy;

import com.projects.mycar.mycar_server.entities.Vehiculo;

import java.time.LocalDate;
import java.util.List;

public interface BusquedaVehiculosStrategy {

    List<Vehiculo> buscar(LocalDate fechaDesde, LocalDate fechaHasta);

}
