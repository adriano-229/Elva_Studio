package edu.egg.tinder.repository;

import edu.egg.tinder.entities.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioFoto extends JpaRepository<Foto, Long> {

}
