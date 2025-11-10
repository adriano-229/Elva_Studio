package com.example.mycar.services.impl;

import com.example.mycar.dto.PaisDTO;
import com.example.mycar.entities.Pais;
import com.example.mycar.repositories.BaseRepository;
import com.example.mycar.repositories.PaisRepository;
import com.example.mycar.services.PaisService;
import com.example.mycar.services.mapper.BaseMapper;
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
