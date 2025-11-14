package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.DireccionDTO;
import com.projects.mycar.mycar_server.entities.Direccion;
import com.projects.mycar.mycar_server.entities.Localidad;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-13T21:15:46-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.17 (Microsoft)"
)
@Component
public class DireccionMapperImpl implements DireccionMapper {

    @Override
    public List<DireccionDTO> toDtoList(List<Direccion> entities) {
        if ( entities == null ) {
            return null;
        }

        List<DireccionDTO> list = new ArrayList<DireccionDTO>( entities.size() );
        for ( Direccion direccion : entities ) {
            list.add( toDto( direccion ) );
        }

        return list;
    }

    @Override
    public DireccionDTO toDto(Direccion entity) {
        if ( entity == null ) {
            return null;
        }

        DireccionDTO.DireccionDTOBuilder<?, ?> direccionDTO = DireccionDTO.builder();

        direccionDTO.localidadId( entityLocalidadId( entity ) );
        direccionDTO.id( entity.getId() );
        direccionDTO.activo( entity.isActivo() );
        direccionDTO.calle( entity.getCalle() );
        direccionDTO.numeracion( entity.getNumeracion() );
        direccionDTO.barrio( entity.getBarrio() );
        direccionDTO.manzana_piso( entity.getManzana_piso() );
        direccionDTO.casa_departamento( entity.getCasa_departamento() );
        direccionDTO.referencia( entity.getReferencia() );

        return direccionDTO.build();
    }

    @Override
    public Direccion toEntity(DireccionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Direccion direccion = new Direccion();

        direccion.setLocalidad( direccionDTOToLocalidad( dto ) );
        direccion.setId( dto.getId() );
        direccion.setActivo( dto.isActivo() );
        direccion.setCalle( dto.getCalle() );
        direccion.setNumeracion( dto.getNumeracion() );
        direccion.setBarrio( dto.getBarrio() );
        direccion.setManzana_piso( dto.getManzana_piso() );
        direccion.setCasa_departamento( dto.getCasa_departamento() );
        direccion.setReferencia( dto.getReferencia() );

        return direccion;
    }

    private Long entityLocalidadId(Direccion direccion) {
        Localidad localidad = direccion.getLocalidad();
        if ( localidad == null ) {
            return null;
        }
        return localidad.getId();
    }

    protected Localidad direccionDTOToLocalidad(DireccionDTO direccionDTO) {
        if ( direccionDTO == null ) {
            return null;
        }

        Localidad localidad = new Localidad();

        localidad.setId( direccionDTO.getLocalidadId() );

        return localidad;
    }
}
