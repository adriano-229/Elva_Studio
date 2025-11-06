package com.projects.gym.gym_app.service.impl;

import com.projects.gym.gym_app.domain.Empresa;
import com.projects.gym.gym_app.domain.Sucursal;
import com.projects.gym.gym_app.repository.EmpresaRepository;
import com.projects.gym.gym_app.repository.SucursalRepository;
import com.projects.gym.gym_app.service.SucursalService;
import com.projects.gym.gym_app.service.dto.SucursalDTO;
import com.projects.gym.gym_app.service.dto.SucursalFormDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SucursalServiceImpl implements SucursalService {

    private final SucursalRepository sucursalRepository;
    private final EmpresaRepository empresaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SucursalDTO> listar() {
        return sucursalRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SucursalDTO> listarActivas() {
        return sucursalRepository.findByEliminadoFalseOrderByNombreAsc().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public SucursalDTO crear(SucursalFormDTO form) {
        Empresa empresa = empresaRepository.findById(form.getEmpresaId())
                .orElseThrow(() -> new EntityNotFoundException("Empresa no encontrada"));

        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(form.getNombre());
        sucursal.setEmpresa(empresa);
        sucursal.setEliminado(false);
        return toDto(sucursalRepository.save(sucursal));
    }

    private SucursalDTO toDto(Sucursal sucursal) {
        Empresa empresa = sucursal.getEmpresa();
        return SucursalDTO.builder()
                .id(sucursal.getId())
                .nombre(sucursal.getNombre())
                .eliminado(sucursal.isEliminado())
                .empresaId(empresa != null ? empresa.getId() : null)
                .empresaNombre(empresa != null ? empresa.getNombre() : null)
                .build();
    }
}
