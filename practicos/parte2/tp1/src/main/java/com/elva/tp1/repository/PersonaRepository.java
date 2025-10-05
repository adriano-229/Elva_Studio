package com.elva.tp1.repository;

import com.elva.tp1.domain.Persona;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends BaseRepository<Persona, Long> {

    @Query("SELECT p FROM Persona p WHERE LOWER(CONCAT(p.nombre, ' ', p.apellido)) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Persona> findAllByNombreApellidoContainsIgnoreCaseOrderByApellido(@Param("search") String search);

    List<Persona> findAllByEmailContainsOrderByEmail(String email);
}
