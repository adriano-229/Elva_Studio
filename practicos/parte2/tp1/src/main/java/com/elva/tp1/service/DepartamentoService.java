package com.elva.tp1.service;

import com.elva.tp1.domain.Departamento;

import java.util.List;

public interface DepartamentoService extends CrudService<Departamento, Long> {
    List<Departamento> findByProvinciaId(Long provinciaId);
}
