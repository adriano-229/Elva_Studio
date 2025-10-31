package com.elva.videogames.business.logic.services;

import com.elva.videogames.business.domain.entities.Videogame;
import com.elva.videogames.business.logic.errors.ResourceNotFoundException;
import com.elva.videogames.business.persistence.repositories.VideogameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VideogameService implements BaseService<Videogame> {

    private final VideogameRepository videogameRepository;

    public VideogameService(VideogameRepository videogameRepository) {
        this.videogameRepository = videogameRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Videogame> findAll() {
        return videogameRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Videogame findById(Long id) {
        return videogameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Videogame not found (id=" + id + ")"));
    }

    @Override
    @Transactional
    public void saveOne(Videogame entity) {
        videogameRepository.save(entity);
    }

    @Override
    @Transactional
    public void updateOne(Long id, Videogame entity) {
        Videogame existing = findById(id); // will throw if not exists
        entity.setId(existing.getId());
        videogameRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!videogameRepository.existsById(id)) {
            throw new ResourceNotFoundException("Videogame not found (id=" + id + ")");
        }
        Optional<Videogame> videogame = videogameRepository.findById(id);
        if (videogame.isPresent()) {
            videogame.get().setInactive(true);
            videogameRepository.save(videogame.get());
        }
    }

    @Transactional(readOnly = true)
    public List<Videogame> findAllActive() {
        return videogameRepository.findAllActive();
    }

    @Transactional(readOnly = true)
    public Videogame findActiveById(Long id) {
        return videogameRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Active videogame not found (id=" + id + ")"));
    }

    @Transactional(readOnly = true)
    public List<Videogame> findByContainsNameActiveIgnoreCase(String title) {
        if (title == null || title.isBlank()) {
            return findAllActive();
        }
        return videogameRepository.findActiveByContainsNameIgnoreCase(title);
    }
}
