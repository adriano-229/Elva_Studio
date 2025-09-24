package com.projects.gym.gym_app.service;

import com.projects.gym.gym_app.domain.enums.TipoEmpleado;
import com.projects.gym.gym_app.service.dto.EmpleadoDTO;
import com.projects.gym.gym_app.service.dto.EmpleadoFormDTO;
import com.projects.gym.gym_app.service.dto.EmpleadoListadoDTO;
import java.util.List;
import java.util.Optional;

public interface EmpleadoService {
    List<EmpleadoDTO> listarProfesoresActivos();

    Optional<EmpleadoDTO> buscarProfesorActivoPorUsuario(String nombreUsuario);

    List<EmpleadoListadoDTO> listar(String filtro, TipoEmpleado tipo);

    EmpleadoFormDTO crear(EmpleadoFormDTO form);

    EmpleadoFormDTO actualizar(Long id, EmpleadoFormDTO form);

    EmpleadoFormDTO buscarPorId(Long id);

    void cambiarEstado(Long id, boolean activo);
}
