package com.projects.mycar.mycar_admin.dao.impl;

import org.springframework.stereotype.Repository;

import com.projects.mycar.mycar_admin.domain.LocalidadDTO;

@Repository
public class LocalidadRestDaoImpl extends BaseRestDaoImpl<LocalidadDTO, Long>{

	public LocalidadRestDaoImpl() {
		super(LocalidadDTO.class, LocalidadDTO[].class, "http://168.181.186.171:8083/api/v1/localidades");
	}

}
