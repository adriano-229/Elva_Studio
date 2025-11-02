package com.projects.gym.gym_app.repository;

import com.projects.gym.gym_app.domain.Rutina;
import com.projects.gym.gym_app.domain.enums.EstadoRutina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RutinaRepository extends JpaRepository<Rutina, Long> {
    Optional<Rutina> findBySocio_NumeroSocioAndEstadoRutina(Long socioId, EstadoRutina estado);

    long countByProfesor_IdAndEstadoRutina(Long profesorId, EstadoRutina estado);

    long countByProfesor_Id(Long profesorId);

    List<Rutina> findTop5ByProfesor_IdAndEstadoRutinaOrderByFechaFinalizaAsc(Long profesorId, EstadoRutina estado);

    List<Rutina> findByEstadoRutinaOrderByFechaFinalizaDesc(EstadoRutina estado);

    List<Rutina> findByProfesor_IdAndEstadoRutinaOrderByFechaFinalizaDesc(Long profesorId, EstadoRutina estado);

    List<Rutina> findBySocio_NumeroSocioAndEstadoRutinaOrderByFechaFinalizaDesc(Long numeroSocio, EstadoRutina estado);
}
