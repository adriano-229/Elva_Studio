package com.adriano.library.business.persistence.repository;

import com.adriano.library.business.domain.entity.Publisher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends BaseRepository<Publisher, Long> {

    List<Publisher> findAllByNameContainingIgnoreCase(String name);
}

