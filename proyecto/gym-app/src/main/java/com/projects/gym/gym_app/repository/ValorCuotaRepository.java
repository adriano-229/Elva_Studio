package com.projects.gym.gym_app.repository;

import com.projects.gym.gym_app.domain.ValorCuota;
import com.projects.gym.gym_app.domain.enums.Mes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValorCuotaRepository extends JpaRepository<ValorCuota, String> {
    boolean existsByAnioAndMes(Integer anio, Mes mes);
}
