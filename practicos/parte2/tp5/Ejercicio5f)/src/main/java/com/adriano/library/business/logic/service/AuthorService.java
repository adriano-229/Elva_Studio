package com.adriano.library.business.logic.service;

import com.adriano.library.business.domain.entity.Author;
import com.adriano.library.business.persistence.repository.AuthorRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService extends BaseService<Author, Long> {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository repository) {
        super(repository);
        this.authorRepository = repository;
    }

    public Optional<Author> findByName(String name) {
        List<Author> authors = authorRepository.findAllByNameIgnoreCase(name);
        return authors.isEmpty() ? Optional.empty() : Optional.of(authors.getFirst());

    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void save(Author entity) {
        super.save(entity);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void update(Long id, Author entity) {
        super.update(id, entity);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}

