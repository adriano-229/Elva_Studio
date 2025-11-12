package com.projects.mycar.mycar_cliente.dao.impl;

import org.springframework.stereotype.Repository;

import com.projects.mycar.mycar_cliente.dao.ImagenRestDao;
import com.projects.mycar.mycar_cliente.domain.ImagenDTO;

@Repository
public class ImagenRestDaoImpl extends BaseRestDaoImpl<ImagenDTO, Long> implements ImagenRestDao{

	public ImagenRestDaoImpl() {
		super(ImagenDTO.class, ImagenDTO[].class, "http://localhost:9000/api/v1/imagen/binario");
	}

}
