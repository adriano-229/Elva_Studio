package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.DepartamentoDTO;
import com.projects.mycar.mycar_server.dto.ProvinciaDTO;
import com.projects.mycar.mycar_server.entities.Departamento;
import com.projects.mycar.mycar_server.entities.Pais;
import com.projects.mycar.mycar_server.entities.Provincia;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-13T21:15:46-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.17 (Microsoft)"
)
@Component
public class ProvinciaMapperImpl implements ProvinciaMapper {

    @Autowired
    private DepartamentoMapper departamentoMapper;

    @Override
    public List<ProvinciaDTO> toDtoList(List<Provincia> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ProvinciaDTO> list = new ArrayList<ProvinciaDTO>( entities.size() );
        for ( Provincia provincia : entities ) {
            list.add( toDto( provincia ) );
        }

        return list;
    }

    @Override
    public ProvinciaDTO toDto(Provincia entity) {
        if ( entity == null ) {
            return null;
        }

        ProvinciaDTO.ProvinciaDTOBuilder<?, ?> provinciaDTO = ProvinciaDTO.builder();

        provinciaDTO.paisId( entityPaisId( entity ) );
        provinciaDTO.id( entity.getId() );
        provinciaDTO.activo( entity.isActivo() );
        provinciaDTO.nombre( entity.getNombre() );
        provinciaDTO.departamentos( departamentoSetToDepartamentoDTOSet( entity.getDepartamentos() ) );

        return provinciaDTO.build();
    }

    @Override
    public Provincia toEntity(ProvinciaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Provincia provincia = new Provincia();

        provincia.setPais( provinciaDTOToPais( dto ) );
        provincia.setId( dto.getId() );
        provincia.setActivo( dto.isActivo() );
        provincia.setNombre( dto.getNombre() );
        provincia.setDepartamentos( departamentoDTOSetToDepartamentoSet( dto.getDepartamentos() ) );

        return provincia;
    }

    private Long entityPaisId(Provincia provincia) {
        Pais pais = provincia.getPais();
        if ( pais == null ) {
            return null;
        }
        return pais.getId();
    }

    protected Set<DepartamentoDTO> departamentoSetToDepartamentoDTOSet(Set<Departamento> set) {
        if ( set == null ) {
            return null;
        }

        Set<DepartamentoDTO> set1 = new LinkedHashSet<DepartamentoDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Departamento departamento : set ) {
            set1.add( departamentoMapper.toDto( departamento ) );
        }

        return set1;
    }

    protected Pais provinciaDTOToPais(ProvinciaDTO provinciaDTO) {
        if ( provinciaDTO == null ) {
            return null;
        }

        Pais pais = new Pais();

        pais.setId( provinciaDTO.getPaisId() );

        return pais;
    }

    protected Set<Departamento> departamentoDTOSetToDepartamentoSet(Set<DepartamentoDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Departamento> set1 = new LinkedHashSet<Departamento>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( DepartamentoDTO departamentoDTO : set ) {
            set1.add( departamentoMapper.toEntity( departamentoDTO ) );
        }

        return set1;
    }
}
