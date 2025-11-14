package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.DireccionDTO;
import com.projects.mycar.mycar_server.dto.LocalidadDTO;
import com.projects.mycar.mycar_server.entities.Departamento;
import com.projects.mycar.mycar_server.entities.Direccion;
import com.projects.mycar.mycar_server.entities.Localidad;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-13T21:15:46-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.17 (Microsoft)"
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
