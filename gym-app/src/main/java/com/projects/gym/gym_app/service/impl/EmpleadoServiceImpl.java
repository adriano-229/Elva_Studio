package com.projects.gym.gym_app.service.impl;

import com.projects.gym.gym_app.domain.Empleado;
import com.projects.gym.gym_app.domain.enums.TipoEmpleado;
import com.projects.gym.gym_app.repository.EmpleadoRepository;
import com.projects.gym.gym_app.service.EmpleadoService;
import com.projects.gym.gym_app.service.dto.EmpleadoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    @Override
    public List<EmpleadoDTO> listarProfesoresActivos() {
        Comparator<String> stringComparator = Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER);

        return empleadoRepository
                .findByActivoTrueAndTipo(TipoEmpleado.PROFESOR)
                .stream()
                .sorted(Comparator.comparing(Empleado::getApellido, stringComparator)
                        .thenComparing(Empleado::getNombre, stringComparator))
                .map(emp -> EmpleadoDTO.builder()
                        .id(emp.getId())
                        .nombreCompleto(emp.getApellido() + ", " + emp.getNombre())
                        .tipo(emp.getTipo())
                        .activo(emp.isActivo())
                        .build())
                .toList();
    }
}
