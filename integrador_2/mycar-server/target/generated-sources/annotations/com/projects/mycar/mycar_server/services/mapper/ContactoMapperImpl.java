package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.ContactoDTO;
import com.projects.mycar.mycar_server.entities.Contacto;
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
public class ContactoMapperImpl implements ContactoMapper {

    @Override
    public Contacto toEntity(ContactoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Contacto contacto = new Contacto();

        contacto.setId( dto.getId() );
        contacto.setActivo( dto.isActivo() );
        contacto.setTipoContacto( dto.getTipoContacto() );
        contacto.setObservacion( dto.getObservacion() );

        return contacto;
    }

    @Override
    public ContactoDTO toDto(Contacto entity) {
        if ( entity == null ) {
            return null;
        }

        ContactoDTO.ContactoDTOBuilder<?, ?> contactoDTO = ContactoDTO.builder();

        contactoDTO.id( entity.getId() );
        contactoDTO.activo( entity.isActivo() );
        contactoDTO.tipoContacto( entity.getTipoContacto() );
        contactoDTO.observacion( entity.getObservacion() );

        return contactoDTO.build();
    }

    @Override
    public List<ContactoDTO> toDtoList(List<Contacto> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ContactoDTO> list = new ArrayList<ContactoDTO>( entities.size() );
        for ( Contacto contacto : entities ) {
            list.add( toDto( contacto ) );
        }

        return list;
    }
}
