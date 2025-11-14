package com.projects.mycar.mycar_admin.service.impl;

import com.projects.mycar.mycar_admin.dao.BaseRestDao;
import com.projects.mycar.mycar_admin.domain.ProvinciaDTO;
import org.springframework.stereotype.Service;


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
