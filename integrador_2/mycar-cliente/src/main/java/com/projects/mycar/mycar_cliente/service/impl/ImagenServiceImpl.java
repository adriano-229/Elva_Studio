package com.projects.mycar.mycar_cliente.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.mycar.mycar_cliente.dao.BaseRestDao;
import com.projects.mycar.mycar_cliente.dao.ImagenRestDao;
import com.projects.mycar.mycar_cliente.domain.ImagenDTO;
import com.projects.mycar.mycar_cliente.service.ImagenService;

@Service
public class ImagenServiceImpl extends BaseServiceImpl<ImagenDTO, Long> implements ImagenService{
	
	@Autowired
	private ImagenRestDao daoImagen;
	
	public ImagenServiceImpl(BaseRestDao<ImagenDTO, Long> dao) {
		super(dao);
	}

	@Override
	protected void validar(ImagenDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
