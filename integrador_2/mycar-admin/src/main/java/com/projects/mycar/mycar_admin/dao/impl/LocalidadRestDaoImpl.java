package com.projects.mycar.mycar_admin.dao.impl;

import org.springframework.stereotype.Repository;

import com.example.mycar.mycar_admin.domain.LocalidadDTO;

@Repository
public class LocalidadRestDaoImpl extends BaseRestDaoImpl<LocalidadDTO, Long>{

	public LocalidadRestDaoImpl() {
		super(LocalidadDTO.class, LocalidadDTO[].class, "http://localhost:9000/api/v1/localidades");
	}

}
