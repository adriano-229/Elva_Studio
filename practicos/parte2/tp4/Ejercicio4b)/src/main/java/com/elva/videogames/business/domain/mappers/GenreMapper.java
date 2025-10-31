package com.elva.videogames.business.domain.mappers;

import com.elva.videogames.business.domain.dtos.GenreDTO;
import com.elva.videogames.business.domain.entities.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreDTO toDTO(Genre genre);
    Genre toEntity(GenreDTO dto);
}
