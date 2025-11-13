package com.projects.mycar.mycar_server.services.impl;

import com.projects.mycar.mycar_server.dto.ImagenDTO;
import com.projects.mycar.mycar_server.entities.Imagen;
import com.projects.mycar.mycar_server.repositories.BaseRepository;
import com.projects.mycar.mycar_server.services.ImagenService;
import com.projects.mycar.mycar_server.services.mapper.BaseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagenServiceImpl extends BaseServiceImpl<Imagen, ImagenDTO, Long> implements ImagenService {

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
