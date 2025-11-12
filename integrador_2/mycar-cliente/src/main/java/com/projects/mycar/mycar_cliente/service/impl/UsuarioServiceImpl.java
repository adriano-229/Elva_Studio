package com.projects.mycar.mycar_cliente.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.mycar.mycar_cliente.dao.BaseRestDao;
import com.projects.mycar.mycar_cliente.dao.UsuarioRestDao;
import com.projects.mycar.mycar_cliente.domain.UsuarioDTO;
import com.projects.mycar.mycar_cliente.service.UsuarioService;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<UsuarioDTO, Long> implements UsuarioService{
	
	@Autowired
	private UsuarioRestDao daoUsuario;
	
	public UsuarioServiceImpl(BaseRestDao<UsuarioDTO, Long> dao) {
		super(dao);

	}

	@Override
	protected void validar(UsuarioDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
