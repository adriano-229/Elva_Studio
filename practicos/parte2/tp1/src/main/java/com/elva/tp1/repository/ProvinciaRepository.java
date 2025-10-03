package com.elva.tp1.repository;

import com.elva.tp1.domain.Pais;
import com.elva.tp1.domain.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {

    Optional<Pais> findByNombreOrderByAsc(String nombreProvincia);


    Optional<Pais> findByNombrePaisOrderByAsc(String nombrePais);


}
