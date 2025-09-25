package com.projects.gym.gym_app.repository;

import com.projects.gym.gym_app.domain.DetalleEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleEjercicioRepository extends JpaRepository<DetalleEjercicio, Long> { }