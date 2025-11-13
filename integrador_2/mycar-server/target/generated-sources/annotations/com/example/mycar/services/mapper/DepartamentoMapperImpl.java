package com.example.mycar.services.mapper;

import com.example.mycar.dto.DepartamentoDTO;
import com.example.mycar.dto.LocalidadDTO;
import com.example.mycar.entities.Departamento;
import com.example.mycar.entities.Localidad;
import com.example.mycar.entities.Provincia;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-13T13:03:13-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.8 (BellSoft)"
)
@Component
public class DepartamentoMapperImpl implements DepartamentoMapper {

    @Autowired
    private LocalidadMapper localidadMapper;

    @Override
    public List<DepartamentoDTO> toDtoList(List<Departamento> entities) {
        if ( entities == null ) {
            return null;
        }

        List<DepartamentoDTO> list = new ArrayList<DepartamentoDTO>( entities.size() );
        for ( Departamento departamento : entities ) {
            list.add( toDto( departamento ) );
        }

        return list;
    }

    @Override
    public DepartamentoDTO toDto(Departamento entity) {
        if ( entity == null ) {
            return null;
        }

        DepartamentoDTO.DepartamentoDTOBuilder<?, ?> departamentoDTO = DepartamentoDTO.builder();

        departamentoDTO.provinciaId( entityProvinciaId( entity ) );
        departamentoDTO.id( entity.getId() );
        departamentoDTO.activo( entity.isActivo() );
        departamentoDTO.nombre( entity.getNombre() );
        departamentoDTO.localidades( localidadSetToLocalidadDTOSet( entity.getLocalidades() ) );

        return departamentoDTO.build();
    }

    @Override
    public Departamento toEntity(DepartamentoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Departamento departamento = new Departamento();

        departamento.setProvincia( departamentoDTOToProvincia( dto ) );
        departamento.setId( dto.getId() );
        departamento.setActivo( dto.isActivo() );
        departamento.setNombre( dto.getNombre() );
        departamento.setLocalidades( localidadDTOSetToLocalidadSet( dto.getLocalidades() ) );

        return departamento;
    }

    private Long entityProvinciaId(Departamento departamento) {
        Provincia provincia = departamento.getProvincia();
        if ( provincia == null ) {
            return null;
        }
        return provincia.getId();
    }

    protected Set<LocalidadDTO> localidadSetToLocalidadDTOSet(Set<Localidad> set) {
        if ( set == null ) {
            return null;
        }

        Set<LocalidadDTO> set1 = new LinkedHashSet<LocalidadDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Localidad localidad : set ) {
            set1.add( localidadMapper.toDto( localidad ) );
        }

        return set1;
    }

    protected Provincia departamentoDTOToProvincia(DepartamentoDTO departamentoDTO) {
        if ( departamentoDTO == null ) {
            return null;
        }

        Provincia provincia = new Provincia();

        provincia.setId( departamentoDTO.getProvinciaId() );

        return provincia;
    }

    protected Set<Localidad> localidadDTOSetToLocalidadSet(Set<LocalidadDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Localidad> set1 = new LinkedHashSet<Localidad>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( LocalidadDTO localidadDTO : set ) {
            set1.add( localidadMapper.toEntity( localidadDTO ) );
        }

        return set1;
    }
}
