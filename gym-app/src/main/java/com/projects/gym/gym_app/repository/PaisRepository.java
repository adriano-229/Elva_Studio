package com.projects.gym.gym_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projects.gym.gym_app.domain.Pais;

public interface PaisRepository extends JpaRepository<Pais, String> {

    // Equivalente a tu @Query: 
    // "SELECT p FROM Pais p WHERE p.nombre = :nombre AND p.eliminado = FALSE"
    Pais findByNombreAndEliminadoFalse(String nombre);

    // Equivalente a: 
    // "SELECT p FROM Pais p WHERE p.eliminado = FALSE"
    java.util.List<Pais> findAllByEliminadoFalse();

    List<Pais> findByEliminadoFalseOrderByNombreAsc();

}