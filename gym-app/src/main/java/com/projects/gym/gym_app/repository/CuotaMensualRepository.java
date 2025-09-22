package com.projects.gym.gym_app.repository;

import com.projects.gym.gym_app.domain.CuotaMensual;
import com.projects.gym.gym_app.domain.enums.EstadoCuota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CuotaMensualRepository extends JpaRepository<CuotaMensual, String> {
    List<CuotaMensual> findBySocioIdAndEstado(Long socioId, EstadoCuota estado);
    List<CuotaMensual> findByIdIn(Collection<String> ids);
}
