package com.projects.gym.gym_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projects.gym.gym_app.domain.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento,String> {
    Departamento findByProvinciaIdAndNombreAndEliminadoFalse(String idProvincia,String nombre);
    List<Departamento> findAllByEliminadoFalse();
    List<Departamento> findAllByEliminadoFalseAndProvinciaId(String idProvincia);

    @org.springframework.data.jpa.repository.Query("""
    select d from Departamento d
    where d.eliminado=false and d.provincia.id=:provinciaId
    order by d.nombre
    """)
    List<Departamento> findActivasByProvincia(String provinciaId);
}