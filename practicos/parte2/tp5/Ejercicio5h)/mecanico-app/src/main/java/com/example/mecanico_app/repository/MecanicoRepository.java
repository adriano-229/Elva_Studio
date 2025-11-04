package com.example.mecanico_app.repository;

import com.example.mecanico_app.domain.Mecanico;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MecanicoRepository extends JpaRepository<Mecanico, UUID> {

    Optional<Mecanico> findByLegajoAndEliminadoFalse(String legajo);

    Optional<Mecanico> findByIdAndEliminadoFalse(UUID id);

    Optional<Mecanico> findByUsuarioNombreAndEliminadoFalse(String nombre);
}
