package com.example.mycar.strategy;

import java.time.LocalDate;
import java.util.List;

import com.example.mycar.entities.Vehiculo;

public interface BusquedaVehiculosStrategy {
	
	List<Vehiculo> buscar(LocalDate fechaDesde, LocalDate fechaHasta);

}
