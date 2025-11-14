package com.projects.mycar.mycar_admin.service.impl;

import com.example.mycar.mycar_admin.domain.DetalleFacturaDTO;
import com.projects.mycar.mycar_admin.dao.BaseRestDao;

public class DetalleFacturaServiceImpl extends BaseServiceImpl<DetalleFacturaDTO, Long>{

	public DetalleFacturaServiceImpl(BaseRestDao<DetalleFacturaDTO, Long> dao) {
		super(dao);
	}

	@Override
	protected void validar(DetalleFacturaDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeSave(DetalleFacturaDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
