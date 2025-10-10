package com.elva.tp1.repository;

import com.elva.tp1.domain.Direccion;
import com.elva.tp1.domain.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresaRepository extends BaseRepository<Empresa, Long> {

    List<Empresa> findAllByRazonSocialIsContainingIgnoreCaseOrderByRazonSocial(String razonSocial);

    List<Empresa> findAllByDireccion(Direccion direccion);

}