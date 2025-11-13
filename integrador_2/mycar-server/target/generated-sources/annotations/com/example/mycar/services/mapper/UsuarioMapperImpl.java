package com.example.mycar.services.mapper;

import com.example.mycar.dto.UsuarioDTO;
import com.example.mycar.entities.Usuario;
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
public class UsuarioMapperImpl implements UsuarioMapper {

    @Autowired
    private PersonaMapper personaMapper;

    @Override
    public Usuario toEntity(UsuarioDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setId( dto.getId() );
        usuario.setActivo( dto.isActivo() );
        usuario.setNombreUsuario( dto.getNombreUsuario() );
        usuario.setClave( dto.getClave() );
        usuario.setRol( dto.getRol() );
        usuario.setPersona( personaMapper.toEntity( dto.getPersona() ) );

        return usuario;
    }

    @Override
    public UsuarioDTO toDto(Usuario entity) {
        if ( entity == null ) {
            return null;
        }

        UsuarioDTO.UsuarioDTOBuilder<?, ?> usuarioDTO = UsuarioDTO.builder();

        usuarioDTO.id( entity.getId() );
        usuarioDTO.activo( entity.isActivo() );
        usuarioDTO.nombreUsuario( entity.getNombreUsuario() );
        usuarioDTO.clave( entity.getClave() );
        usuarioDTO.rol( entity.getRol() );
        usuarioDTO.persona( personaMapper.toDto( entity.getPersona() ) );

        return usuarioDTO.build();
    }

    @Override
    public List<UsuarioDTO> toDtoList(List<Usuario> entities) {
        if ( entities == null ) {
            return null;
        }

        List<UsuarioDTO> list = new ArrayList<UsuarioDTO>( entities.size() );
        for ( Usuario usuario : entities ) {
            list.add( toDto( usuario ) );
        }

        return list;
    }
}
