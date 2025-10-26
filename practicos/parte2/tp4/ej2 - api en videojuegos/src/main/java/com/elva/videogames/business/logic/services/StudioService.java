package com.elva.videogames.business.logic.services;

import com.elva.videogames.business.domain.entities.Studio;
import com.elva.videogames.business.logic.errors.ResourceNotFoundException;
import com.elva.videogames.business.persistence.repositories.StudioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudioService implements BaseService<Studio> {

    private final StudioRepository studioRepository;

    public StudioService(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Studio> findAll() {
        return studioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Studio findById(Long id) {
        return studioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Studio not found (id=" + id + ")"));
    }

    @Override
    @Transactional
    public void saveOne(Studio entity) {
        studioRepository.save(entity);
    }

    @Override
    @Transactional
    public void updateOne(Long id, Studio entity) {
        Studio existing = findById(id);
        entity.setId(existing.getId());
        studioRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!studioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Studio not found (id=" + id + ")");
        }
        studioRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Studio> findAllActive() {
        return studioRepository.findAllActive();
    }

    @Override
    @Transactional(readOnly = true)
    public Studio findActiveById(Long id) {
        return studioRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Active studio not found (id=" + id + ")"));
    }
}
