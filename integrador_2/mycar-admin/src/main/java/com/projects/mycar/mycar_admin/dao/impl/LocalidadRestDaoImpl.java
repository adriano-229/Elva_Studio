package com.projects.mycar.mycar_admin.dao.impl;

import com.projects.mycar.mycar_admin.domain.LocalidadDTO;
import org.springframework.stereotype.Repository;

@Repository
public class LocalidadRestDaoImpl extends BaseRestDaoImpl<LocalidadDTO, Long>{

	public LocalidadRestDaoImpl() {
        super(LocalidadDTO.class, LocalidadDTO[].class, "http://localhost:8083/api/v1/localidades");
	}

}
