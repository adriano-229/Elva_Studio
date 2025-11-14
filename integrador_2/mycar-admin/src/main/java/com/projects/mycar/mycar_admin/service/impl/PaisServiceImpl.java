package com.projects.mycar.mycar_admin.service.impl;

import com.projects.mycar.mycar_admin.dao.BaseRestDao;
import com.projects.mycar.mycar_admin.domain.PaisDTO;
import org.springframework.stereotype.Service;

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
