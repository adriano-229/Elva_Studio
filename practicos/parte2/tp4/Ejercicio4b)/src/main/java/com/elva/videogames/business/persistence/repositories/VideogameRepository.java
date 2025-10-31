package com.elva.videogames.business.persistence.repositories;

import com.elva.videogames.business.domain.entities.Videogame;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideogameRepository extends BaseRepository<Videogame> {
    // Template method pattern - implementing base methods with specific queries
    @Override
    @Query("SELECT v FROM Videogame v WHERE v.inactive = false")
    List<Videogame> findAllActive();

    @Override
    @Query("SELECT v FROM Videogame v WHERE v.id = :id AND v.inactive = false")
    Optional<Videogame> findActiveById(@Param("id") Long id);

    // Custom query for videogame search
    @Query("SELECT v FROM Videogame v WHERE LOWER(v.name) LIKE LOWER(CONCAT('%', :name, '%')) AND v.inactive = false")
    List<Videogame> findActiveByContainsNameIgnoreCase(@Param("name") String name);
}
