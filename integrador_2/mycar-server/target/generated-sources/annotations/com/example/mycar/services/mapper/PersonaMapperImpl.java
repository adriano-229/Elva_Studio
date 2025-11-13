package com.example.mycar.services.mapper;

import com.example.mycar.dto.PersonaDTO;
import com.example.mycar.entities.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-13T13:03:13-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.8 (BellSoft)"
)
@Component
public class PersonaMapperImpl implements PersonaMapper {

    @Autowired
    private DireccionMapper direccionMapper;
    @Autowired
    private ImagenMapper imagenMapper;

    @Override
    public Persona toEntity(PersonaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Persona persona = new Persona();

        persona.setId( dto.getId() );
        persona.setActivo( dto.isActivo() );
        persona.setNombre( dto.getNombre() );
        persona.setApellido( dto.getApellido() );
        persona.setFechaNacimiento( dto.getFechaNacimiento() );
        persona.setTipoDocumento( dto.getTipoDocumento() );
        persona.setNumeroDocumento( dto.getNumeroDocumento() );
        persona.setImagen( imagenMapper.toEntity( dto.getImagen() ) );
        persona.setDireccion( direccionMapper.toEntity( dto.getDireccion() ) );

        return persona;
    }

    @Override
    public PersonaDTO toDto(Persona entity) {
        if ( entity == null ) {
            return null;
        }

        PersonaDTO.PersonaDTOBuilder<?, ?> personaDTO = PersonaDTO.builder();

        personaDTO.id( entity.getId() );
        personaDTO.activo( entity.isActivo() );
        personaDTO.nombre( entity.getNombre() );
        personaDTO.apellido( entity.getApellido() );
        personaDTO.fechaNacimiento( entity.getFechaNacimiento() );
        personaDTO.tipoDocumento( entity.getTipoDocumento() );
        personaDTO.numeroDocumento( entity.getNumeroDocumento() );
        personaDTO.imagen( imagenMapper.toDto( entity.getImagen() ) );
        personaDTO.direccion( direccionMapper.toDto( entity.getDireccion() ) );

        return personaDTO.build();
    }

    @Override
    public List<PersonaDTO> toDtoList(List<Persona> entities) {
        if ( entities == null ) {
            return null;
        }

        List<PersonaDTO> list = new ArrayList<PersonaDTO>( entities.size() );
        for ( Persona persona : entities ) {
            list.add( toDto( persona ) );
        }

        return list;
    }
}
