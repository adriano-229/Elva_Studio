package com.example.mycar.strategy;

import com.example.mycar.entities.Vehiculo;
import com.example.mycar.repositories.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service("alquiladosStrategy")
public class VehiculosAlquiladosStrategy implements BusquedaVehiculosStrategy {

    private final VehiculoRepository vehiculoRepository;

    public VehiculosAlquiladosStrategy(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    @Override
    public List<Vehiculo> buscar(LocalDate fechaDesde, LocalDate fechaHasta) {
        return vehiculoRepository.findVehiculosAlquilados(fechaDesde, fechaHasta);
    }

}
