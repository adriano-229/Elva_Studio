package com.example.mycar.services.mapper;

import com.example.mycar.dto.DireccionDTO;
import com.example.mycar.dto.LocalidadDTO;
import com.example.mycar.entities.Departamento;
import com.example.mycar.entities.Direccion;
import com.example.mycar.entities.Localidad;
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
public class LocalidadMapperImpl implements LocalidadMapper {

    @Autowired
    private DireccionMapper direccionMapper;

    @Override
    public List<LocalidadDTO> toDtoList(List<Localidad> entities) {
        if ( entities == null ) {
            return null;
        }

        List<LocalidadDTO> list = new ArrayList<LocalidadDTO>( entities.size() );
        for ( Localidad localidad : entities ) {
            list.add( toDto( localidad ) );
        }

        return list;
    }

    @Override
    public LocalidadDTO toDto(Localidad entity) {
        if ( entity == null ) {
            return null;
        }

        LocalidadDTO.LocalidadDTOBuilder<?, ?> localidadDTO = LocalidadDTO.builder();

        localidadDTO.departamentoId( entityDepartamentoId( entity ) );
        localidadDTO.id( entity.getId() );
        localidadDTO.activo( entity.isActivo() );
        localidadDTO.nombre( entity.getNombre() );
        localidadDTO.codigoPostal( entity.getCodigoPostal() );
        localidadDTO.direcciones( direccionMapper.toDtoList( entity.getDirecciones() ) );

        return localidadDTO.build();
    }

    @Override
    public Localidad toEntity(LocalidadDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Localidad localidad = new Localidad();

        localidad.setDepartamento( localidadDTOToDepartamento( dto ) );
        localidad.setId( dto.getId() );
        localidad.setActivo( dto.isActivo() );
        localidad.setNombre( dto.getNombre() );
        localidad.setCodigoPostal( dto.getCodigoPostal() );
        localidad.setDirecciones( direccionDTOListToDireccionList( dto.getDirecciones() ) );

        return localidad;
    }

    private Long entityDepartamentoId(Localidad localidad) {
        Departamento departamento = localidad.getDepartamento();
        if ( departamento == null ) {
            return null;
        }
        return departamento.getId();
    }

    protected Departamento localidadDTOToDepartamento(LocalidadDTO localidadDTO) {
        if ( localidadDTO == null ) {
            return null;
        }

        Departamento departamento = new Departamento();

        departamento.setId( localidadDTO.getDepartamentoId() );

        return departamento;
    }

    protected List<Direccion> direccionDTOListToDireccionList(List<DireccionDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Direccion> list1 = new ArrayList<Direccion>( list.size() );
        for ( DireccionDTO direccionDTO : list ) {
            list1.add( direccionMapper.toEntity( direccionDTO ) );
        }

        return list1;
    }
}
