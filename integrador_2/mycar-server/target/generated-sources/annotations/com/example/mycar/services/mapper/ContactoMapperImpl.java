package com.example.mycar.services.mapper;

import com.example.mycar.dto.ContactoDTO;
import com.example.mycar.entities.Contacto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-13T13:03:13-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.8 (BellSoft)"
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
