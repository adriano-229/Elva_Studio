package com.colmena.videojuegos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colmena.videojuegos.entities.Categoria;

@Repository
public interface RepositorioCategoria extends JpaRepository<Categoria, Long>{

}
