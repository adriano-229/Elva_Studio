package com.elva.videogames.business.persistence.repositories;

import com.elva.videogames.business.domain.entities.Studio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudioRepository extends BaseRepository<Studio> {
    // Template method pattern - implementing base methods with specific queries
    @Override
    @Query("SELECT s FROM Studio s WHERE s.inactive = false")
    List<Studio> findAllActive();

    @Override
    @Query("SELECT s FROM Studio s WHERE s.id = :id AND s.inactive = false")
    Optional<Studio> findActiveById(@Param("id") Long id);
}
