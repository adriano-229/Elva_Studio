package com.projects.mycar.mycar_admin.service.impl;

import org.springframework.stereotype.Service;

import com.example.mycar.mycar_admin.domain.DepartamentoDTO;
import com.projects.mycar.mycar_admin.dao.BaseRestDao;

@Service
public class DepartamentoServiceImpl extends BaseServiceImpl<DepartamentoDTO, Long>{

	public DepartamentoServiceImpl(BaseRestDao<DepartamentoDTO, Long> dao) {
		super(dao);
	}

	@Override
	protected void validar(DepartamentoDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeSave(DepartamentoDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
