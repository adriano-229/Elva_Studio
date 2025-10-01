package edu.egg.tinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.egg.tinder.entities.Zona;

@Repository
public interface RepositorioZona extends JpaRepository<Zona, Long>{

}
