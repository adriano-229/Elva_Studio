package com.elva.videogames.business.domain.mappers;

import com.elva.videogames.business.domain.dtos.StudioDTO;
import com.elva.videogames.business.domain.entities.Studio;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudioMapper {
    StudioDTO toDTO(Studio studio);
    Studio toEntity(StudioDTO dto);
}
