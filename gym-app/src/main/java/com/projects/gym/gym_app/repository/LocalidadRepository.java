package com.projects.gym.gym_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projects.gym.gym_app.domain.Localidad;

public interface LocalidadRepository extends JpaRepository<Localidad,String> {
    Localidad findByDepartamentoIdAndNombreAndEliminadoFalse(String idDepartamento,String nombre);
    List<Localidad> findAllByEliminadoFalse();
    List<Localidad> findAllByEliminadoFalseAndDepartamentoId(String idDepartamento);

    @org.springframework.data.jpa.repository.Query("""
    select l from Localidad l
    where l.eliminado=false and l.departamento.id=:departamentoId
    order by l.nombre
    """)
    List<Localidad> findActivasByDepartamento(String departamentoId);
}
