package com.projects.gym.gym_app.repository;

import com.projects.gym.gym_app.domain.Empleado;
import com.projects.gym.gym_app.domain.enums.TipoEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    List<Empleado> findByActivoTrueAndTipo(TipoEmpleado tipo);

    Optional<Empleado> findByUsuario_NombreUsuarioAndActivoTrue(String nombreUsuario);
}
