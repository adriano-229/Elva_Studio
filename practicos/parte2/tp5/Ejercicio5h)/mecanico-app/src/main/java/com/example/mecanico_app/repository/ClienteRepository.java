package com.example.mecanico_app.repository;

import com.example.mecanico_app.domain.Cliente;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    Optional<Cliente> findByDocumentoAndEliminadoFalse(String documento);

    Optional<Cliente> findByIdAndEliminadoFalse(UUID id);
}
