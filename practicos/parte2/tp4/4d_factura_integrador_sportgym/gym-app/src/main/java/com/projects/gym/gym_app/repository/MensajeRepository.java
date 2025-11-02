package com.projects.gym.gym_app.repository;

import com.projects.gym.gym_app.domain.Mensaje;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensajeRepository extends JpaRepository<Mensaje, String> {
    Page<Mensaje> findByEliminadoFalse(Pageable pageable);
}
