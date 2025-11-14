package com.projects.mycar.mycar_admin.dao.impl;

import org.springframework.stereotype.Repository;

import com.projects.mycar.mycar_admin.domain.DepartamentoDTO;

@Repository
public class DepartamentoRestDaoImpl extends BaseRestDaoImpl<DepartamentoDTO, Long>{

	public DepartamentoRestDaoImpl() {
		super(DepartamentoDTO.class, DepartamentoDTO[].class, "http://localhost:8083/api/v1/departamentos");
	}

}
