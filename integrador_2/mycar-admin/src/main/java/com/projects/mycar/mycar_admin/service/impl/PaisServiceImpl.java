package com.projects.mycar.mycar_admin.service.impl;

import org.springframework.stereotype.Service;

import com.example.mycar.mycar_admin.domain.PaisDTO;
import com.projects.mycar.mycar_admin.dao.BaseRestDao;

@Service
public class PaisServiceImpl extends BaseServiceImpl<PaisDTO, Long>{

	public PaisServiceImpl(BaseRestDao<PaisDTO, Long> dao) {
		super(dao);
	}

	@Override
	protected void validar(PaisDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeSave(PaisDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}
	

}
