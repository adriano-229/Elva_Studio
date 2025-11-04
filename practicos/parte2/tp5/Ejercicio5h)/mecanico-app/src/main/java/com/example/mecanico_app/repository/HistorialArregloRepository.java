package com.example.mecanico_app.repository;

import com.example.mecanico_app.domain.HistorialArreglo;
import com.example.mecanico_app.domain.Vehiculo;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorialArregloRepository extends JpaRepository<HistorialArreglo, UUID> {

    List<HistorialArreglo> findByVehiculoAndEliminadoFalseOrderByFechaArregloDesc(Vehiculo vehiculo);
}
