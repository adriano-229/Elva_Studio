package com.projects.mycar.mycar_server.scheduler;

import com.projects.mycar.mycar_server.entities.Vehiculo;
import com.projects.mycar.mycar_server.enums.EstadoVehiculo;
import com.projects.mycar.mycar_server.repositories.AlquilerRepository;
import com.projects.mycar.mycar_server.repositories.CaracteristicaVehiculoRepository;
import com.projects.mycar.mycar_server.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
