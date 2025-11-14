package com.projects.mycar.mycar_admin.dao.impl;

import com.projects.mycar.mycar_admin.domain.DetalleFacturaDTO;

public class DetalleFacturaRestDaoImpl extends BaseRestDaoImpl<DetalleFacturaDTO, Long>{

	public DetalleFacturaRestDaoImpl(Class<DetalleFacturaDTO> entityClass, Class<DetalleFacturaDTO[]> entityArrayClass,
			String baseUrl) {
		super(entityClass, entityArrayClass, baseUrl);
		
	}

}
