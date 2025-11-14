package com.projects.mycar.mycar_admin.dao.impl;

import org.springframework.stereotype.Repository;

import com.projects.mycar.mycar_admin.domain.PaisDTO;

@Repository
public class PaisRestDaoImpl extends BaseRestDaoImpl<PaisDTO, Long>{

	public PaisRestDaoImpl() {
		super(PaisDTO.class, PaisDTO[].class, "http://168.181.186.171:8083/api/v1/paises");
	}

}
