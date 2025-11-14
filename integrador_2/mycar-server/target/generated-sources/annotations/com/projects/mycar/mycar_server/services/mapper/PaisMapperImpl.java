package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.PaisDTO;
import com.projects.mycar.mycar_server.dto.ProvinciaDTO;
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
public class PaisMapperImpl implements PaisMapper {

    @Autowired
    private ProvinciaMapper provinciaMapper;

    @Override
    public Pais toEntity(PaisDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Pais pais = new Pais();

        pais.setId( dto.getId() );
        pais.setActivo( dto.isActivo() );
        pais.setNombre( dto.getNombre() );
        pais.setProvincias( provinciaDTOSetToProvinciaSet( dto.getProvincias() ) );

        return pais;
    }

    @Override
    public PaisDTO toDto(Pais entity) {
        if ( entity == null ) {
            return null;
        }

        PaisDTO.PaisDTOBuilder<?, ?> paisDTO = PaisDTO.builder();

        paisDTO.id( entity.getId() );
        paisDTO.activo( entity.isActivo() );
        paisDTO.nombre( entity.getNombre() );
        paisDTO.provincias( provinciaSetToProvinciaDTOSet( entity.getProvincias() ) );

        return paisDTO.build();
    }

    @Override
    public List<PaisDTO> toDtoList(List<Pais> entities) {
        if ( entities == null ) {
            return null;
        }

        List<PaisDTO> list = new ArrayList<PaisDTO>( entities.size() );
        for ( Pais pais : entities ) {
            list.add( toDto( pais ) );
        }

        return list;
    }

    protected Set<Provincia> provinciaDTOSetToProvinciaSet(Set<ProvinciaDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Provincia> set1 = new LinkedHashSet<Provincia>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( ProvinciaDTO provinciaDTO : set ) {
            set1.add( provinciaMapper.toEntity( provinciaDTO ) );
        }

        return set1;
    }

    protected Set<ProvinciaDTO> provinciaSetToProvinciaDTOSet(Set<Provincia> set) {
        if ( set == null ) {
            return null;
        }

        Set<ProvinciaDTO> set1 = new LinkedHashSet<ProvinciaDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Provincia provincia : set ) {
            set1.add( provinciaMapper.toDto( provincia ) );
        }

        return set1;
    }
}
