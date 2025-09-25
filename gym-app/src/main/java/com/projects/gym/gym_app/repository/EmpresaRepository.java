package com.projects.gym.gym_app.repository;

import com.projects.gym.gym_app.domain.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpresaRepository extends JpaRepository<Empresa, String> {
    List<Empresa> findByEliminadoFalseOrderByNombreAsc();
}
