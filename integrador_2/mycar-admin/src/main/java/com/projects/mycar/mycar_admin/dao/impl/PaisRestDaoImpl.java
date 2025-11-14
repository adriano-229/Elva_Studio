package com.projects.mycar.mycar_admin.dao.impl;

import com.projects.mycar.mycar_admin.domain.PaisDTO;
import org.springframework.stereotype.Repository;

@Repository
public class PaisRestDaoImpl extends BaseRestDaoImpl<PaisDTO, Long>{

	public PaisRestDaoImpl() {
		super(PaisDTO.class, PaisDTO[].class, "http://localhost:9000/api/v1/paises");
	}

}
