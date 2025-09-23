package com.projects.gym.gym_app.repository;

import com.projects.gym.gym_app.domain.Rutina;
import com.projects.gym.gym_app.domain.enums.EstadoRutina;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RutinaRepository extends JpaRepository<Rutina, Long> {
    Optional<Rutina> findBySocio_NumeroSocioAndEstadoRutina(Long socioId, EstadoRutina estado);
}