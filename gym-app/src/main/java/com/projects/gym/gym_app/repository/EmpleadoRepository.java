package com.projects.gym.gym_app.repository;

import com.projects.gym.gym_app.domain.Empleado;
import com.projects.gym.gym_app.domain.enums.TipoEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    List<Empleado> findByActivoTrueAndTipo(TipoEmpleado tipo);
}
