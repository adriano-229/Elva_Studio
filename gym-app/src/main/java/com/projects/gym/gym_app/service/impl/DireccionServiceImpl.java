package com.projects.gym.gym_app.service.impl;


import com.projects.gym.gym_app.domain.Direccion;
import com.projects.gym.gym_app.domain.Localidad;
import com.projects.gym.gym_app.repository.DireccionRepository;
import com.projects.gym.gym_app.repository.LocalidadRepository;
import com.projects.gym.gym_app.service.DireccionService;
import com.projects.gym.gym_app.service.dto.DireccionDTO;
import com.projects.gym.gym_app.service.mapper.DireccionMapper;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DireccionServiceImpl implements DireccionService {

    private final DireccionRepository direccionRepo;
    private final LocalidadRepository localidadRepo;

    @Override
    public DireccionDTO crear(DireccionDTO dto) {
        validar(dto);
        Localidad loc = localidadRepo.findById(dto.getLocalidadId())
                .orElseThrow(() -> new EntityNotFoundException("Localidad no encontrada"));
        Direccion entity = DireccionMapper.toEntity(dto, loc);
        entity.setEliminado(false);
        return DireccionMapper.toDto(direccionRepo.save(entity));
    }

    @Override @Transactional(readOnly = true)
    public DireccionDTO buscarPorId(String id) {
        Direccion e = direccionRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dirección no encontrada"));
        return DireccionMapper.toDto(e);
    }

    @Override
    public DireccionDTO modificar(String id, DireccionDTO dto) {
        Direccion e = direccionRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dirección no encontrada"));
        validar(dto);
        Localidad loc = localidadRepo.findById(dto.getLocalidadId())
                .orElseThrow(() -> new EntityNotFoundException("Localidad no encontrada"));

        DireccionMapper.updateEntity(e, dto, loc);

        return DireccionMapper.toDto(e);
    }

    @Override
    public void eliminarLogico(String id) {
        Direccion e = direccionRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dirección no encontrada"));
        e.setEliminado(true);
    }

    private void validar(DireccionDTO d) {
        // ejemplo de validación de negocio
        if (d.getCalle().length() < 2) {
            throw new IllegalArgumentException("La calle es demasiado corta");
        }
    }
}
