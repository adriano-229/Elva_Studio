package com.projects.gym.gym_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projects.gym.gym_app.domain.Provincia;

public interface ProvinciaRepository extends JpaRepository<Provincia,String> {
    Provincia findByPaisIdAndNombreAndEliminadoFalse(String idPais,String nombre);
    List<Provincia> findAllByEliminadoFalse();
    List<Provincia> findAllByEliminadoFalseAndPaisId(String idPais);


    @org.springframework.data.jpa.repository.Query("""
    select p from Provincia p
    where p.eliminado=false and p.pais.id=:paisId
    order by p.nombre
    """)
    List<Provincia> findActivasByPais(String paisId);
}
