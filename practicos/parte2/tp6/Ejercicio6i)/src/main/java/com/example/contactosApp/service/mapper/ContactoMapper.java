package com.example.contactosApp.service.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.contactosApp.domain.Contacto;
import com.example.contactosApp.domain.ContactoCorreoElectronico;
import com.example.contactosApp.domain.ContactoTelefonico;
import com.example.contactosApp.domain.dto.ContactoCorreoElectronicoDTO;
import com.example.contactosApp.domain.dto.ContactoDTO;
import com.example.contactosApp.domain.dto.ContactoTelefonicoDTO;

@Mapper(componentModel = "spring", uses = {EmpresaMapper.class, PersonaMapper.class})
public abstract class ContactoMapper implements BaseMapper<Contacto, ContactoDTO> {

    @Autowired
    protected EmpresaMapper empresaMapper;

    @Autowired
    protected PersonaMapper personaMapper;

    @Override
    public Contacto toEntity(ContactoDTO dto) {
        if (dto instanceof ContactoCorreoElectronicoDTO cce) {
            return ContactoCorreoElectronico.builder()
                    .email(cce.getEmail())
                    .empresa(cce.getEmpresa() != null ? empresaMapper.toEntity(cce.getEmpresa()) : null)
                    .persona(cce.getPersona() != null ? personaMapper.toEntity(cce.getPersona()) : null)
                    .observacion(cce.getObservacion())
                    .tipoContacto(cce.getTipoContacto())
                    .activo(cce.isActivo())
                    .build();
        } else if (dto instanceof ContactoTelefonicoDTO ct) {
            return ContactoTelefonico.builder()
            		.telefono(ct.getTelefono())
                    .tipoTelefono(ct.getTipoTelefono())
                    .empresa(ct.getEmpresa() != null ? empresaMapper.toEntity(ct.getEmpresa()) : null)
                    .persona(ct.getPersona() != null ? personaMapper.toEntity(ct.getPersona()) : null)
                    .observacion(ct.getObservacion())
                    .tipoContacto(ct.getTipoContacto())
                    .activo(ct.isActivo())
                    .build();
        }
        return null;
    }

    @Override
    public ContactoDTO toDto(Contacto entity) {
        if (entity instanceof ContactoCorreoElectronico cce) {
            return ContactoCorreoElectronicoDTO.builder()
            		.id(cce.getId())
                    .email(cce.getEmail())
                    .empresa(cce.getEmpresa() != null ? empresaMapper.toDto(cce.getEmpresa()) : null)
                    .persona(cce.getPersona() != null ? personaMapper.toDto(cce.getPersona()) : null)
                    .observacion(cce.getObservacion())
                    .tipoContacto(cce.getTipoContacto())
                    .activo(cce.isActivo())
                    .build();
        } else if (entity instanceof ContactoTelefonico ct) {
            return ContactoTelefonicoDTO.builder()
            		.id(ct.getId())
                    .telefono(ct.getTelefono())
                    .tipoTelefono(ct.getTipoTelefono())
                    .empresa(ct.getEmpresa() != null ? empresaMapper.toDto(ct.getEmpresa()) : null)
                    .persona(ct.getPersona() != null ? personaMapper.toDto(ct.getPersona()) : null)
                    .observacion(ct.getObservacion())
                    .tipoContacto(ct.getTipoContacto())
                    .activo(ct.isActivo())
                    .build();
        }
        return null;
    }
}
