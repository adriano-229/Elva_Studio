package com.adriano.library.business.persistence.repository;

import com.adriano.library.business.domain.entity.Author;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends BaseRepository<Author, Long> {

    List<Author> findAllByNameIgnoreCase(String name);
}

