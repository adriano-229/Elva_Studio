package com.example.mecanico_app.repository;

import com.example.mecanico_app.domain.Cliente;
import com.example.mecanico_app.domain.Vehiculo;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo, UUID> {

    Optional<Vehiculo> findByPatenteAndEliminadoFalse(String patente);

    List<Vehiculo> findByClienteAndEliminadoFalse(Cliente cliente);

    List<Vehiculo> findByEliminadoFalseOrderByPatenteAsc();
}
