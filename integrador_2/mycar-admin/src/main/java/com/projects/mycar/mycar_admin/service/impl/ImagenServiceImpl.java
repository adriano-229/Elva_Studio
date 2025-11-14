package com.projects.mycar.mycar_admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mycar.mycar_admin.domain.ImagenDTO;
import com.example.mycar.mycar_admin.domain.VehiculoDTO;
import com.projects.mycar.mycar_admin.dao.BaseRestDao;
import com.projects.mycar.mycar_admin.dao.impl.ImagenRestDaoImpl;
import com.projects.mycar.mycar_admin.service.ImagenService;


@Service
public class ImagenServiceImpl  extends BaseServiceImpl<ImagenDTO, Long> implements ImagenService{ 
	
	@Autowired
	private ImagenRestDaoImpl daoImagen;

	public ImagenServiceImpl(BaseRestDao<ImagenDTO, Long> dao) {
	super(dao);

}

	@Override
	protected void validar(ImagenDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeSave(ImagenDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public ImagenDTO guardarImagen(ImagenDTO dto) throws Exception {
        try {
            validar(dto);
            beforeSave(dto);
            return daoImagen.crearImagen(dto);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


	
}
