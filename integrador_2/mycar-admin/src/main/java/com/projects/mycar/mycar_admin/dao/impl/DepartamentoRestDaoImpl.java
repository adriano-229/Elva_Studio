package com.projects.mycar.mycar_admin.dao.impl;

import org.springframework.stereotype.Repository;

import com.example.mycar.mycar_admin.domain.DepartamentoDTO;

@Repository
public class DepartamentoRestDaoImpl extends BaseRestDaoImpl<DepartamentoDTO, Long>{

	public DepartamentoRestDaoImpl() {
		super(DepartamentoDTO.class, DepartamentoDTO[].class, "http://localhost:9000/api/v1/departamentos");
	}

}
