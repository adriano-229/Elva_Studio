package com.example.mycar.services.impl;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.mycar.entities.Vehiculo;
import com.example.mycar.enums.EstadoVehiculo;
import com.example.mycar.repositories.AlquilerRepository;
import com.example.mycar.repositories.CaracteristicaVehiculoRepository;
import com.example.mycar.repositories.VehiculoRepository;

@Service
public class VehiculoScheduler {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private AlquilerRepository alquilerRepository;
    
    @Autowired
    private CaracteristicaVehiculoRepository caracteristicaVehiculoRepository;

    @Scheduled(cron = "0 0 0 * * *") // todos los días a medianoche
    public void actualizarEstadosVehiculos() {
        LocalDate hoy = LocalDate.now();
        List<Vehiculo> vehiculos = vehiculoRepository.findAll();

        for (Vehiculo v : vehiculos) {
            boolean alquilado = alquilerRepository.existeAlquilerActivoEnFecha(v, hoy);
            v.setEstadoVehiculo(alquilado ? EstadoVehiculo.Alquilado : EstadoVehiculo.Disponible);
            
        }

        vehiculoRepository.saveAll(vehiculos);
        
        caracteristicaVehiculoRepository.actualizarTotales();
    }
	

}
