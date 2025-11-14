package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.DocumentacionDTO;
import com.projects.mycar.mycar_server.entities.Documentacion;
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
public class DocumentacionMapperImpl implements DocumentacionMapper {

    @Override
    public Documentacion toEntity(DocumentacionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Documentacion documentacion = new Documentacion();

        documentacion.setId( dto.getId() );
        documentacion.setActivo( dto.isActivo() );
        documentacion.setTipoDocumentacion( dto.getTipoDocumentacion() );
        documentacion.setPathArchivo( dto.getPathArchivo() );
        documentacion.setObservacion( dto.getObservacion() );
        documentacion.setNombreArchivo( dto.getNombreArchivo() );

        return documentacion;
    }

    @Override
    public DocumentacionDTO toDto(Documentacion entity) {
        if ( entity == null ) {
            return null;
        }

        DocumentacionDTO.DocumentacionDTOBuilder<?, ?> documentacionDTO = DocumentacionDTO.builder();

        documentacionDTO.id( entity.getId() );
        documentacionDTO.activo( entity.isActivo() );
        documentacionDTO.tipoDocumentacion( entity.getTipoDocumentacion() );
        documentacionDTO.pathArchivo( entity.getPathArchivo() );
        documentacionDTO.observacion( entity.getObservacion() );
        documentacionDTO.nombreArchivo( entity.getNombreArchivo() );

        return documentacionDTO.build();
    }

    @Override
    public List<DocumentacionDTO> toDtoList(List<Documentacion> entities) {
        if ( entities == null ) {
            return null;
        }

        List<DocumentacionDTO> list = new ArrayList<DocumentacionDTO>( entities.size() );
        for ( Documentacion documentacion : entities ) {
            list.add( toDto( documentacion ) );
        }

        return list;
    }
}
