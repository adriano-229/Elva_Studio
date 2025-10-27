package com.adriano.library.business.persistence.repository;

import com.adriano.library.business.domain.entity.Publisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends BaseRepository<Publisher, Long> {

    List<Publisher> findAllByNameContainingIgnoreCase(String name);
}

