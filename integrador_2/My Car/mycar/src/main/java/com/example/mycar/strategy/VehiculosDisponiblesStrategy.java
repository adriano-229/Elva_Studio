package com.example.mycar.strategy;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mycar.entities.Vehiculo;
import com.example.mycar.repositories.VehiculoRepository;

@Service("disponiblesStrategy")
public class VehiculosDisponiblesStrategy implements BusquedaVehiculosStrategy{
	 private final VehiculoRepository vehiculoRepository;

    public VehiculosDisponiblesStrategy(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    @Override
    public List<Vehiculo> buscar(LocalDate fechaDesde, LocalDate fechaHasta) {
        return vehiculoRepository.findVehiculosDisponibles(fechaDesde, fechaHasta);
    }
}
