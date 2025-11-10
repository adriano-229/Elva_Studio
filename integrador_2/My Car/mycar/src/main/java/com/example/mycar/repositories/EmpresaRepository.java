package com.example.mycar.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.mycar.entities.Empresa;

@Repository
public interface EmpresaRepository extends BaseRepository<Empresa, Long> {

    Optional<Empresa> findTop1ByActivoTrueOrderByIdAsc();
}
