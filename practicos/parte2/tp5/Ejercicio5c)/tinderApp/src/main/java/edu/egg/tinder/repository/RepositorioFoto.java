package edu.egg.tinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.egg.tinder.entities.Foto;

@Repository
public interface RepositorioFoto extends JpaRepository<Foto,Long>{

}
