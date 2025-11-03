package com.projects.gym.gym_app.repository;

import com.projects.gym.gym_app.domain.DetalleFactura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, String> {}