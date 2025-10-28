package com.adriano.library.business.logic.facade;

import com.adriano.library.business.domain.entity.Author;
import com.adriano.library.business.persistence.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorMapperHelper {

    private final AuthorRepository authorRepository;

    public AuthorMapperHelper(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> mapAuthorIdsToAuthors(List<Long> ids) {
        if (ids == null) return List.of();
        return authorRepository.findAllById(ids);
    }

    public List<Long> mapAuthorsToAuthorIds(List<Author> authors) {
        if (authors == null) return List.of();
        return authors.stream()
                .map(Author::getId)
                .toList();
    }
}
