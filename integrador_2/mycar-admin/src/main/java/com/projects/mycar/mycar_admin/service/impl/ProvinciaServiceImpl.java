package com.projects.mycar.mycar_admin.service.impl;

import org.springframework.stereotype.Service;

import com.projects.mycar.mycar_admin.domain.ProvinciaDTO;
import com.projects.mycar.mycar_admin.dao.BaseRestDao;


@Service
public class ProvinciaServiceImpl extends BaseServiceImpl<ProvinciaDTO, Long> {

	public ProvinciaServiceImpl(BaseRestDao<ProvinciaDTO, Long> dao) {
		super(dao);
	}

	@Override
	protected void validar(ProvinciaDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeSave(ProvinciaDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

 

   
}
