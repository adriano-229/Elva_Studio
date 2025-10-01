package com.colmena.videojuegos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colmena.videojuegos.entities.Estudio;

@Repository
public interface RepositorioEstudio extends JpaRepository<Estudio, Long> {

}
