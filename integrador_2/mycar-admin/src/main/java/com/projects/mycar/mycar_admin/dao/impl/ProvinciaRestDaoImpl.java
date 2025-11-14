package com.projects.mycar.mycar_admin.dao.impl;

import org.springframework.stereotype.Repository;

import com.example.mycar.mycar_admin.domain.ProvinciaDTO;

@Repository
public class ProvinciaRestDaoImpl extends BaseRestDaoImpl<ProvinciaDTO, Long>{

	public ProvinciaRestDaoImpl() {
		super(ProvinciaDTO.class, ProvinciaDTO[].class, "http://localhost:9000/api/v1/provincias");
	}

}
