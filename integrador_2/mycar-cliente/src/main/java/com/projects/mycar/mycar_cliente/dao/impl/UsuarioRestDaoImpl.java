package com.projects.mycar.mycar_cliente.dao.impl;

import org.springframework.stereotype.Repository;

import com.projects.mycar.mycar_cliente.dao.UsuarioRestDao;
import com.projects.mycar.mycar_cliente.domain.UsuarioDTO;

@Repository
public class UsuarioRestDaoImpl extends BaseRestDaoImpl<UsuarioDTO, Long> implements UsuarioRestDao {

	public UsuarioRestDaoImpl() {
		super(UsuarioDTO.class, UsuarioDTO[].class, "http://localhost:9000/api/v1/auth");
		// TODO Auto-generated constructor stub
	}

}
