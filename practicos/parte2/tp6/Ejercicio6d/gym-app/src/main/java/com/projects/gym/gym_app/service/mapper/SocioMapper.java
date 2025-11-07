package com.projects.gym.gym_app.service.mapper;

import com.projects.gym.gym_app.domain.*;
import com.projects.gym.gym_app.domain.enums.Rol;
import com.projects.gym.gym_app.domain.enums.TipoDocumento;
import com.projects.gym.gym_app.service.dto.SocioFormDTO;


public class SocioMapper {
    public static void fillPersonaFromDto(Persona p, SocioFormDTO d) {
        p.setNombre(d.getNombre());
        p.setApellido(d.getApellido());
        p.setFechaNacimiento(d.getFechaNacimiento());
        p.setTipoDocumento(TipoDocumento.valueOf(d.getTipoDocumento()));
        p.setNumeroDocumento(d.getNumeroDocumento());
        p.setTelefono(d.getTelefono());
        p.setCorreoElectronico(d.getCorreoElectronico());
    }

    public static void fillUsuarioFromDto(Usuario u, SocioFormDTO d) {
        u.setNombreUsuario(d.getNombreUsuario());
        u.setClave(d.getClave()); // si tenés encoder, aplicarlo en el service
        u.setRol(Rol.valueOf(d.getRol()));
        u.setEliminado(false);
    }

    public static void fillDireccionFromDto(Direccion dir, SocioFormDTO d, Localidad loc) {
        dir.setCalle(d.getCalle());
        dir.setNumeracion(d.getNumeracion());
        dir.setBarrio(d.getBarrio());
        dir.setManzanaPiso(d.getManzanaPiso());
        dir.setCasaDepartamento(d.getCasaDepartamento());
        dir.setReferencia(d.getReferencia());
        dir.setEliminado(false);
        dir.setLocalidad(loc);
    }

    public static SocioFormDTO toDto(Socio socio) {
        SocioFormDTO dto = new SocioFormDTO();
        dto.setId(socio.getId() != null ? String.valueOf(socio.getId()) : null);
        dto.setNombre(socio.getNombre());
        dto.setApellido(socio.getApellido());
        dto.setFechaNacimiento(socio.getFechaNacimiento());
        if (socio.getTipoDocumento() != null) {
            dto.setTipoDocumento(socio.getTipoDocumento().name());
        }
        dto.setNumeroDocumento(socio.getNumeroDocumento());
        dto.setTelefono(socio.getTelefono());
        dto.setCorreoElectronico(socio.getCorreoElectronico());
        dto.setNumeroSocio(socio.getNumeroSocio());

        Sucursal sucursal = socio.getSucursal();
        if (sucursal != null) {
            dto.setSucursalId(sucursal.getId());
            dto.setSucursalNombre(sucursal.getNombre());
        }

        Usuario usuario = socio.getUsuario();
        if (usuario != null) {
            dto.setNombreUsuario(usuario.getNombreUsuario());
            if (usuario.getRol() != null) {
                dto.setRol(usuario.getRol().name());
            }
        }

        Direccion direccion = socio.getDireccion();
        if (direccion != null) {
            dto.setDireccionId(direccion.getId());
            Localidad localidad = direccion.getLocalidad();
            if (localidad != null) {
                dto.setLocalidadId(localidad.getId());
            }
            dto.setCalle(direccion.getCalle());
            dto.setNumeracion(direccion.getNumeracion());
            dto.setBarrio(direccion.getBarrio());
            dto.setManzanaPiso(direccion.getManzanaPiso());
            dto.setCasaDepartamento(direccion.getCasaDepartamento());
            dto.setReferencia(direccion.getReferencia());
        }

        dto.setEliminado(socio.isEliminado());
        return dto;
    }

}
