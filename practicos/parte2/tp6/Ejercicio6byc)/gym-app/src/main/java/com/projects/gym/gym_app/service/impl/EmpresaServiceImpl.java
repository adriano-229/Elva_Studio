package com.projects.gym.gym_app.service.impl;

import com.projects.gym.gym_app.domain.Empresa;
import com.projects.gym.gym_app.repository.EmpresaRepository;
import com.projects.gym.gym_app.service.EmpresaService;
import com.projects.gym.gym_app.service.dto.EmpresaDTO;
import com.projects.gym.gym_app.service.dto.EmpresaFormDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EmpresaDTO> listar() {
        return empresaRepository.findByEliminadoFalseOrderByNombreAsc().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public EmpresaDTO crear(EmpresaFormDTO form) {
        Empresa empresa = new Empresa();
        empresa.setNombre(form.getNombre());
        empresa.setTelefono(form.getTelefono());
        empresa.setCorreoElectronico(form.getCorreoElectronico());
        empresa.setEliminado(false);
        return toDto(empresaRepository.save(empresa));
    }

    private EmpresaDTO toDto(Empresa empresa) {
        return EmpresaDTO.builder()
                .id(empresa.getId())
                .nombre(empresa.getNombre())
                .telefono(empresa.getTelefono())
                .correoElectronico(empresa.getCorreoElectronico())
                .eliminado(empresa.isEliminado())
                .build();
    }
}
