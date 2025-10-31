package com.elva.videogames.controllers.rest;

import com.elva.videogames.business.domain.dtos.StudioDTO;
import com.elva.videogames.business.domain.entities.Studio;
import com.elva.videogames.business.domain.mappers.StudioMapper;
import com.elva.videogames.business.logic.services.BaseService;
import com.elva.videogames.business.logic.services.StudioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/studios")
public class StudioRestController extends BaseRestController<Studio, StudioDTO> {

    private final StudioService studioService;
    private final StudioMapper mapper;

    public StudioRestController(StudioService studioService, StudioMapper mapper) {
        this.studioService = studioService;
        this.mapper = mapper;
    }

    @Override
    protected BaseService<Studio> getService() {
        return studioService;
    }

    @Override
    protected StudioDTO toDTO(Studio entity) {
        return mapper.toDTO(entity);
    }

    @Override
    protected Studio toEntity(StudioDTO dto) {
        return mapper.toEntity(dto);
    }

    @Override
    protected List<StudioDTO> toDTOList(List<Studio> entities) {
        return entities.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}

