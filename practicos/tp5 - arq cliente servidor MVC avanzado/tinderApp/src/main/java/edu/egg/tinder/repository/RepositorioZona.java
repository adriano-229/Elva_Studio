package edu.egg.tinder.repository;

import edu.egg.tinder.entities.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioZona extends JpaRepository<Zona, Long> {

}
