package com.projects.mycar.mycar_admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mycar.mycar_admin.domain.CodigoDescuentoDTO;
import com.projects.mycar.mycar_admin.dao.BaseRestDao;
import com.projects.mycar.mycar_admin.dao.impl.CodigoDescuentoRestDaoImpl;
import com.projects.mycar.mycar_admin.service.CodigoDescuentoService;

@Service
public class CodigoDescuentoServiceImpl extends BaseServiceImpl<CodigoDescuentoDTO, Long> implements CodigoDescuentoService{

	@Autowired
	private CodigoDescuentoRestDaoImpl daoCodigo;
	
	
	public CodigoDescuentoServiceImpl(BaseRestDao<CodigoDescuentoDTO, Long> dao) {
		super(dao);
	}

	@Override
	public boolean existePorCodigo(String codigo) throws Exception {
		try {
			return daoCodigo.existePorCodigo(codigo);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	protected void validar(CodigoDescuentoDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeSave(CodigoDescuentoDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
