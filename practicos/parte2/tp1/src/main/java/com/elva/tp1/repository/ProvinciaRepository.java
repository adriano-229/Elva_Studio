package com.elva.tp1.repository;

import com.elva.tp1.domain.Pais;
import com.elva.tp1.domain.Provincia;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvinciaRepository extends BaseRepository<Provincia, Long> {

    List<Provincia> findAllByNombreIsContainingIgnoreCaseOrderByNombre(String nombre);

    List<Provincia> findAllByPais_NombreOrderByNombre(String paisNombre);



}
