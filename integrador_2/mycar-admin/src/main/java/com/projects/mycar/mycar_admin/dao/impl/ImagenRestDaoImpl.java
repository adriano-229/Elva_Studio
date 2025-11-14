package com.projects.mycar.mycar_admin.dao.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.mycar.mycar_admin.domain.ImagenDTO;
import com.example.mycar.mycar_admin.domain.VehiculoDTO;
import com.projects.mycar.mycar_admin.dao.ImagenRestDao;

@Repository
public class ImagenRestDaoImpl extends BaseRestDaoImpl<ImagenDTO, Long> implements ImagenRestDao {

	public ImagenRestDaoImpl() {
		super(ImagenDTO.class, ImagenDTO[].class, "http://localhost:9000/api/v1/imagen" );
	}
	
	public ImagenDTO crearImagen(ImagenDTO dto) throws Exception {
		
		try {
	        ResponseEntity<ImagenDTO> response =
	                restTemplate.postForEntity(baseUrl, dto, ImagenDTO.class);

	        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
	            return response.getBody(); 
	        } else {
	            throw new Exception("Error al crear LA IMAGEN. Respuesta vacía o inválida.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception("Error al crear entidad", e);
	    }
			
	}
	
}
