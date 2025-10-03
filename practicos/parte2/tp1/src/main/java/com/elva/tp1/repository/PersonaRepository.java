package com.elva.tp1.repository;

import com.elva.tp1.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona ,Long> {

    Optional<Persona> findByNombre(String nombre);

    Optional<Persona> findByApellido(String nombre);

}
