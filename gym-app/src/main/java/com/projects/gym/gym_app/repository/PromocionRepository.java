package com.projects.gym.gym_app.repository;

import com.projects.gym.gym_app.domain.Promocion;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromocionRepository extends JpaRepository<Promocion, String> {
    Page<Promocion> findByEliminadoFalse(Pageable pageable);
}
