package com.projects.mycar.mycar_admin.service.impl;

import com.projects.mycar.mycar_admin.dao.BaseRestDao;
import com.projects.mycar.mycar_admin.domain.LocalidadDTO;
import org.springframework.stereotype.Service;

@Service
public class LocalidadServiceImpl extends BaseServiceImpl<LocalidadDTO, Long>{

	public LocalidadServiceImpl(BaseRestDao<LocalidadDTO, Long> dao) {
		super(dao);
	}

	@Override
	protected void validar(LocalidadDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeSave(LocalidadDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
