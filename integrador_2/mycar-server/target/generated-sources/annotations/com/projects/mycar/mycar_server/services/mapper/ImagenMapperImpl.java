package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.ImagenDTO;
import com.projects.mycar.mycar_server.entities.Imagen;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-13T21:15:46-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.17 (Microsoft)"
)
@Component
public class ImagenMapperImpl implements ImagenMapper {

    @Override
    public Imagen toEntity(ImagenDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Imagen.ImagenBuilder imagen = Imagen.builder();

        imagen.nombre( dto.getNombre() );
        imagen.mime( dto.getMime() );
        byte[] contenido = dto.getContenido();
        if ( contenido != null ) {
            imagen.contenido( Arrays.copyOf( contenido, contenido.length ) );
        }
        imagen.tipoImagen( dto.getTipoImagen() );

        return imagen.build();
    }

    @Override
    public ImagenDTO toDto(Imagen entity) {
        if ( entity == null ) {
            return null;
        }

        ImagenDTO.ImagenDTOBuilder<?, ?> imagenDTO = ImagenDTO.builder();

        imagenDTO.id( entity.getId() );
        imagenDTO.activo( entity.isActivo() );
        imagenDTO.nombre( entity.getNombre() );
        imagenDTO.mime( entity.getMime() );
        byte[] contenido = entity.getContenido();
        if ( contenido != null ) {
            imagenDTO.contenido( Arrays.copyOf( contenido, contenido.length ) );
        }
        imagenDTO.tipoImagen( entity.getTipoImagen() );

        return imagenDTO.build();
    }

    @Override
    public List<ImagenDTO> toDtoList(List<Imagen> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ImagenDTO> list = new ArrayList<ImagenDTO>( entities.size() );
        for ( Imagen imagen : entities ) {
            list.add( toDto( imagen ) );
        }

        return list;
    }
}
