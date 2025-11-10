package com.example.mycar.strategy;

import com.example.mycar.entities.Vehiculo;

import java.time.LocalDate;
import java.util.List;

public interface BusquedaVehiculosStrategy {

    List<Vehiculo> buscar(LocalDate fechaDesde, LocalDate fechaHasta);

}
