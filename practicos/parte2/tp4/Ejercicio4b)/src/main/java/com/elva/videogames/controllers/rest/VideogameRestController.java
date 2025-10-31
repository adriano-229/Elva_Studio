package com.elva.videogames.controllers.rest;

import com.elva.videogames.business.domain.dtos.VideogameDTO;
import com.elva.videogames.business.domain.entities.Genre;
import com.elva.videogames.business.domain.entities.Studio;
import com.elva.videogames.business.domain.entities.Videogame;
import com.elva.videogames.business.domain.mappers.VideogameMapper;
import com.elva.videogames.business.logic.services.BaseService;
import com.elva.videogames.business.logic.services.GenreService;
import com.elva.videogames.business.logic.services.StudioService;
import com.elva.videogames.business.logic.services.VideogameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/videogames")
public class VideogameRestController extends BaseRestController<Videogame, VideogameDTO> {


    private final VideogameService videogameService;
    private final GenreService genreService;
    private final StudioService studioService;
    private final VideogameMapper mapper;

    public VideogameRestController(VideogameService videogameService, GenreService genreService, StudioService studioService, VideogameMapper mapper) {
        this.videogameService = videogameService;
        this.genreService = genreService;
        this.studioService = studioService;
        this.mapper = mapper;
    }

    @Override
    protected BaseService<Videogame> getService() {
        return videogameService;
    }

    @Override
    protected VideogameDTO toDTO(Videogame entity) {
        return mapper.toDTO(entity);
    }

    @Override
    protected Videogame toEntity(VideogameDTO dto) {
        Videogame entity = mapper.toEntity(dto);
        if (dto.getStudioId() != null) {
            Studio studio = studioService.findById(dto.getStudioId());
            entity.setStudio(studio);
        }
        if (dto.getGenreId() != null) {
            Genre genre = genreService.findById(dto.getGenreId());
            entity.setGenre(genre);
        }
        return entity;
    }

    @Override
    protected List<VideogameDTO> toDTOList(List<Videogame> entities) {
        return entities.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    // Custom endpoint for search
    @GetMapping("/search")
    public ResponseEntity<List<VideogameDTO>> search(@RequestParam(value = "query", required = false) String name) {
        List<Videogame> videogames = videogameService.findByContainsNameActiveIgnoreCase(name);
        return ResponseEntity.ok(toDTOList(videogames));
    }
}
