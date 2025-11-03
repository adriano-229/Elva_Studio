package com.projects.gym.gym_app.repository;

import com.projects.gym.gym_app.domain.Factura;
import com.projects.gym.gym_app.domain.enums.EstadoFactura;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Factura,String>, JpaSpecificationExecutor<Factura> {
    Optional<Factura> findByNumeroFactura(long numeroFactura);
    Page<Factura> findByEliminadoFalse(Pageable pageable);
    Page<Factura> findByEliminadoFalseAndEstado(EstadoFactura estado, Pageable pageable);
    Page<Factura> findByEliminadoFalseAndFechaFacturaBetween(LocalDate desde, LocalDate hasta, Pageable pageable);
    Page<Factura> findByEliminadoFalseAndSocioId(String socioId, Pageable pageable);
    Optional<Factura> findFirstByEliminadoFalseOrderByNumeroFacturaDesc();
}
