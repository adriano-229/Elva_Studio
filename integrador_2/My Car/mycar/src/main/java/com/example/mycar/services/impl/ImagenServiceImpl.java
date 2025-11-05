package com.example.mycar.services.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mycar.dto.ImagenDTO;
import com.example.mycar.entities.Imagen;
import com.example.mycar.repositories.BaseRepository;
import com.example.mycar.services.ImagenService;
import com.example.mycar.services.mapper.BaseMapper;

@Service
public class ImagenServiceImpl extends BaseServiceImpl<Imagen, ImagenDTO, Long> implements ImagenService{

	public ImagenServiceImpl(BaseRepository<Imagen, Long> baseRepository, BaseMapper<Imagen, ImagenDTO> baseMapper) {
		super(baseRepository, baseMapper);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<ImagenDTO> findAllByIds(Iterable<Long> ids) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Imagen updateEntityFromDto(Imagen entity, ImagenDTO entityDto) throws Exception {
		entity.setContenido(entityDto.getContenido());
		entity.setMime(entityDto.getMime());
		entity.setNombre(entity.getNombre());
		entity.setTipoImagen(entity.getTipoImagen());
		
		return entity;
	}

	@Override
	protected void validate(ImagenDTO entityDto) throws Exception {
		/*if (entityDto.getContenido() == null || entityDto.getContenido().length == 0) {
            throw new RuntimeException("Archivo vacío");
        }*/
		
	}

	@Override
	protected void beforeSave(ImagenDTO dto) throws Exception {
		// Nombre de la imagen
		String nombre = (dto.getNombre() != null && !dto.getNombre().isBlank())
		                ? dto.getNombre().trim().replaceAll("\\s+", "_")
		                : "imagen_desconocida";

		dto.setNombre(nombre);
		
		//agregar mime
		
	}

	@Override
	protected void afterSave(Imagen entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterUpdate(Imagen entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeDelete(Imagen entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterDelete(Imagen entity) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
}
