package com.elva.videogames.controllers.rest;

import com.elva.videogames.business.domain.dtos.GenreDTO;
import com.elva.videogames.business.domain.entities.Genre;
import com.elva.videogames.business.domain.mappers.GenreMapper;
import com.elva.videogames.business.logic.services.BaseService;
import com.elva.videogames.business.logic.services.GenreService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/genres")
public class GenreRestController extends BaseRestController<Genre, GenreDTO> {

    private final GenreService genreService;
    private final GenreMapper mapper;

    public GenreRestController(GenreService genreService, GenreMapper mapper) {
        this.genreService = genreService;
        this.mapper = mapper;
    }

    @Override
    protected BaseService<Genre> getService() {
        return genreService;
    }

    @Override
    protected GenreDTO toDTO(Genre entity) {
        return mapper.toDTO(entity);
    }

    @Override
    protected Genre toEntity(GenreDTO dto) {
        return mapper.toEntity(dto);
    }

    @Override
    protected List<GenreDTO> toDTOList(List<Genre> entities) {
        return entities.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}

