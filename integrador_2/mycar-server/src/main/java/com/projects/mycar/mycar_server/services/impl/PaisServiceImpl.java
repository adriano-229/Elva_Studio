package com.projects.mycar.mycar_server.services.impl;

import com.projects.mycar.mycar_server.dto.PaisDTO;
import com.projects.mycar.mycar_server.entities.Pais;
import com.projects.mycar.mycar_server.repositories.BaseRepository;
import com.projects.mycar.mycar_server.repositories.PaisRepository;
import com.projects.mycar.mycar_server.services.PaisService;
import com.projects.mycar.mycar_server.services.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaisServiceImpl extends BaseServiceImpl<Pais, PaisDTO, Long> implements PaisService {

    @Autowired
    private PaisRepository repository;

    public PaisServiceImpl(BaseRepository<Pais, Long> baseRepository, BaseMapper<Pais, PaisDTO> baseMapper) {
        super(baseRepository, baseMapper);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<PaisDTO> findAllByIds(Iterable<Long> ids) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<PaisDTO> findAllByOrderByNombreAsc() {
        List<Pais> paises = repository.findAllByOrderByNombreAsc();
        return baseMapper.toDtoList(paises);
    }

    @Override
    public List<PaisDTO> findAllActivosOrderByNombreAsc() {
        List<Pais> paises = repository.findAllByActivoTrueOrderByNombreAsc();
        return baseMapper.toDtoList(paises);
    }

    @Override
    protected Pais updateEntityFromDto(Pais entity, PaisDTO entityDto) throws Exception {
        entity.setNombre(entityDto.getNombre());
        return entity;

    }

    @Override
    protected void validate(PaisDTO entityDto) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void beforeSave(PaisDTO entity) throws Exception {
        Optional<Pais> opt = repository.findByActivoTrueAndNombreIgnoreCase(entity.getNombre());

        if (opt.isPresent()) {
            throw new Exception("El pais ya existe");
        }

    }

    @Override
    protected void afterSave(Pais entity) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void afterUpdate(Pais entity) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void beforeDelete(Pais entity) throws Exception {
        if (entity.getProvincias() != null && !entity.getProvincias().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar el país porque tiene provincias asociadas");
        }
    }

    @Override
    protected void afterDelete(Pais entity) throws Exception {
        // TODO Auto-generated method stub

    }

}
