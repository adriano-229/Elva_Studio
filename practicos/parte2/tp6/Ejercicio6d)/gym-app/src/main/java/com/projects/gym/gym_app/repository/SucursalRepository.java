package com.projects.gym.gym_app.repository;

import com.projects.gym.gym_app.domain.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SucursalRepository extends JpaRepository<Sucursal, String> {
    List<Sucursal> findByEliminadoFalseOrderByNombreAsc();
}
