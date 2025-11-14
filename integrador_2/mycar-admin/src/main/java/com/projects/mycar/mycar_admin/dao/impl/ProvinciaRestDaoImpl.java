package com.projects.mycar.mycar_admin.dao.impl;

import org.springframework.stereotype.Repository;

import com.projects.mycar.mycar_admin.domain.ProvinciaDTO;

@Repository
public class ProvinciaRestDaoImpl extends BaseRestDaoImpl<ProvinciaDTO, Long>{

	public ProvinciaRestDaoImpl() {
		super(ProvinciaDTO.class, ProvinciaDTO[].class, "http://168.181.186.171:8083/api/v1/provincias");
	}

}
