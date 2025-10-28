package com.adriano.library.business.logic.service;

import com.adriano.library.business.domain.entity.Publisher;
import com.adriano.library.business.persistence.repository.PublisherRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherService extends BaseService<Publisher, Long> {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository repository) {
        super(repository);
        this.publisherRepository = repository;
    }

    public Optional<Publisher> findByName(String name) {
        List<Publisher> publishers = publisherRepository.findAllByNameContainingIgnoreCase(name);
        return publishers.isEmpty() ? Optional.empty() : Optional.of(publishers.getFirst());
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void save(Publisher entity) {
        super.save(entity);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void update(Long id, Publisher entity) {
        super.update(id, entity);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}

