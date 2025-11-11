package com.example.mycar.repositories;

import com.example.mycar.entities.Empresa;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends BaseRepository<Empresa, Long> {

    Optional<Empresa> findTop1ByActivoTrueOrderByIdAsc();
}
