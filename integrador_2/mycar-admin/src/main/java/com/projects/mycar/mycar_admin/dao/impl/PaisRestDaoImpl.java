package com.projects.mycar.mycar_admin.dao.impl;

import org.springframework.stereotype.Repository;

import com.example.mycar.mycar_admin.domain.PaisDTO;

@Repository
public class PaisRestDaoImpl extends BaseRestDaoImpl<PaisDTO, Long>{

	public PaisRestDaoImpl() {
		super(PaisDTO.class, PaisDTO[].class, "http://localhost:9000/api/v1/paises");
	}

}
