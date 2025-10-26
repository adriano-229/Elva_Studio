package com.elva.videogames.business.logic.services;

import com.elva.videogames.business.domain.entities.Genre;
import com.elva.videogames.business.logic.errors.ResourceNotFoundException;
import com.elva.videogames.business.persistence.repositories.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenreService implements BaseService<Genre> {


    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Genre findById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found (id=" + id + ")"));
    }

    @Override
    @Transactional
    public void saveOne(Genre entity) {
        genreRepository.save(entity);
    }

    @Override
    @Transactional
    public void updateOne(Long id, Genre entity) {
        Genre existing = findById(id);
        entity.setId(existing.getId());
        genreRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!genreRepository.existsById(id)) {
            throw new ResourceNotFoundException("Genre not found (id=" + id + ")");
        }
        genreRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Genre> findAllActive() {
        return genreRepository.findAllActive();
    }

    @Override
    @Transactional(readOnly = true)
    public Genre findActiveById(Long id) {
        return genreRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Active genre not found (id=" + id + ")"));
    }
}
