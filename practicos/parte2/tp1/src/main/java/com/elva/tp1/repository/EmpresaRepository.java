package com.elva.tp1.repository;

import com.elva.tp1.domain.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Optional<Empresa> findByRazonSocialOrderByAsc(String razonSocial);
}

