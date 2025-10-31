package com.elva.videogames.business.persistence.repositories;

import com.elva.videogames.business.domain.entities.Genre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends BaseRepository<Genre> {
    // Template method pattern - implementing base methods with specific queries
    @Override
    @Query("SELECT g FROM Genre g WHERE g.inactive = false")
    List<Genre> findAllActive();

    @Override
    @Query("SELECT g FROM Genre g WHERE g.id = :id AND g.inactive = false")
    Optional<Genre> findActiveById(@Param("id") Long id);
}
