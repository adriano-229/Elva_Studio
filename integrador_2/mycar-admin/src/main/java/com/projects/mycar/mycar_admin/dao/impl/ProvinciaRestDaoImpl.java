package com.projects.mycar.mycar_admin.dao.impl;

import org.springframework.stereotype.Repository;

import com.projects.mycar.mycar_admin.domain.ProvinciaDTO;

@Repository
public class ProvinciaRestDaoImpl extends BaseRestDaoImpl<ProvinciaDTO, Long>{

	public ProvinciaRestDaoImpl() {
		super(ProvinciaDTO.class, ProvinciaDTO[].class, "/api/v1/provincias");
	}

}
