package com.elva.videogames.business.domain.mappers;

import com.elva.videogames.business.domain.dtos.VideogameDTO;
import com.elva.videogames.business.domain.entities.Videogame;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface VideogameMapper {

    @Mappings({
            @Mapping(target = "studioId", source = "studio.id"),
            @Mapping(target = "studioName", source = "studio.name"),
            @Mapping(target = "genreId", source = "genre.id"),
            @Mapping(target = "genreName", source = "genre.name")
    })
    VideogameDTO toDTO(Videogame videogame);

    @InheritInverseConfiguration(name = "toDTO")
    @Mappings({
            @Mapping(target = "studio", ignore = true),
            @Mapping(target = "genre", ignore = true)
    })
    Videogame toEntity(VideogameDTO dto);
}
