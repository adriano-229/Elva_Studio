package com.example.biblioteca.adapter;

import com.example.biblioteca.entities.Autor;
import com.example.biblioteca.entities.dto.AutorDTO;

public class AutorAdapter {

    // De entidad a DTO
    public static AutorDTO toDTO(Autor autor) {
        if (autor == null) return null;

        return AutorDTO.builder()
                .id(autor.getId())
                .activo(autor.isActivo())
                .nombre(autor.getNombre())
                .apellido(autor.getApellido())
                .biografia(autor.getBiografia())
                .build();
    }

    // De DTO a entidad
    public static Autor toEntity(AutorDTO dto) {
        if (dto == null) return null;

        return Autor.builder()
                .id(dto.getId())
                .activo(dto.isActivo())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .biografia(dto.getBiografia())
                .build();
    }
}

