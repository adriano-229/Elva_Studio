package com.projects.mycar.mycar_server.repositories;

import com.projects.mycar.mycar_server.entities.Empresa;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends BaseRepository<Empresa, Long> {

    Optional<Empresa> findTop1ByActivoTrueOrderByIdAsc();
}
