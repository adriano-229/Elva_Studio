package com.elva.videogames.business.persistence.repositories;

import com.elva.videogames.business.domain.entities.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
    // Template method pattern - common methods defined here, implemented by specific repositories
    List<T> findAllActive();

    Optional<T> findActiveById(Long id);
}
