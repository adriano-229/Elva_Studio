package com.projects.mycar.mycar_admin.dao.impl;

import org.springframework.stereotype.Repository;

import com.projects.mycar.mycar_admin.domain.LocalidadDTO;

@Repository
public class LocalidadRestDaoImpl extends BaseRestDaoImpl<LocalidadDTO, Long>{

	public LocalidadRestDaoImpl() {
		super(LocalidadDTO.class, LocalidadDTO[].class, "/api/v1/localidades");
	}

}
