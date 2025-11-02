package com.projects.gym.gym_app.service.mapper;

import com.projects.gym.gym_app.domain.Direccion;
import com.projects.gym.gym_app.domain.Localidad;
import com.projects.gym.gym_app.service.dto.DireccionDTO;

public class DireccionMapper {
    public static DireccionDTO toDto(Direccion e) {
        DireccionDTO d = new DireccionDTO();
        d.setId(e.getId());
        d.setCalle(e.getCalle());
        d.setNumeracion(e.getNumeracion());
        d.setBarrio(e.getBarrio());
        d.setManzanaPiso(e.getManzanaPiso());
        d.setCasaDepartamento(e.getCasaDepartamento());
        d.setReferencia(e.getReferencia());
        d.setEliminado(e.isEliminado());
        if (e.getLocalidad()!=null) d.setLocalidadId(e.getLocalidad().getId());
        return d;
    }
    public static Direccion toEntity(DireccionDTO d, Localidad loc) {
        Direccion e = new Direccion();
        e.setId(d.getId());
        updateEntity(e, d, loc);
        return e;
    }

    public static void updateEntity(Direccion entity, DireccionDTO dto, Localidad localidad) {
        entity.setCalle(dto.getCalle());
        entity.setNumeracion(dto.getNumeracion());
        entity.setBarrio(dto.getBarrio());
        entity.setManzanaPiso(dto.getManzanaPiso());
        entity.setCasaDepartamento(dto.getCasaDepartamento());
        entity.setReferencia(dto.getReferencia());
        entity.setEliminado(dto.isEliminado());
        entity.setLocalidad(localidad);
    }
}
