package com.elva.tp1.repository;

import com.elva.tp1.domain.Direccion;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DireccionRepository extends BaseRepository<Direccion, Long> {

    List<Direccion> findAllByCalleIsContainingIgnoreCaseOrderByCalleOrderByAltura(String calle);

    List<Direccion> findAllByDepartamento_NombreOrderByCalleOrderByAltura(String departamentoNombre);

    List<Direccion> findAllByOrderByCalleAsc();
}
