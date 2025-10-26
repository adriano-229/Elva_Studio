package com.projects.gym.gym_app.repository;

import com.projects.gym.gym_app.domain.FormaDePago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormaDePagoRepository extends JpaRepository<FormaDePago, String> {
    List<FormaDePago> findByEliminadoFalseOrderByTipoPagoAsc();
}
