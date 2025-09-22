package com.projects.gym.gym_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.Query;

import com.projects.gym.gym_app.domain.Socio;

public interface SocioRepository extends JpaRepository<Socio,Long> {
    Optional<Socio> findByNumeroSocio(Long numeroSocio);
    @Query("""
      select s from Socio s
      where s.eliminado = false and
            (lower(s.nombre) like :q or lower(s.apellido) like :q or lower(s.numeroDocumento) like :q)
    """)
    Page<Socio> searchActivos(String q, Pageable pageable);

    Page<Socio> findByEliminadoFalse(Pageable pageable);

}
