package com.projects.mycar.mycar_server.services.impl;

import com.projects.mycar.mycar_server.dto.PaisDTO;
import com.projects.mycar.mycar_server.dto.ProvinciaDTO;
import com.projects.mycar.mycar_server.entities.Provincia;
import com.projects.mycar.mycar_server.repositories.BaseRepository;
import com.projects.mycar.mycar_server.repositories.ProvinciaRepository;
import com.projects.mycar.mycar_server.services.ProvinciaService;
import com.projects.mycar.mycar_server.services.mapper.BaseMapper;
import com.projects.mycar.mycar_server.services.mapper.PaisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProvinciaServiceImpl extends BaseServiceImpl<Provincia, ProvinciaDTO, Long> implements ProvinciaService {

    @Autowired
    private ProvinciaRepository repository;

    @Autowired
    private PaisMapper paisMapper;

    @Autowired
    private PaisServiceImpl paisService;

    public ProvinciaServiceImpl(BaseRepository<Provincia, Long> baseRepository,
                                BaseMapper<Provincia, ProvinciaDTO> baseMapper) {
        super(baseRepository, baseMapper);
    }

    @Override
    public List<ProvinciaDTO> findAllByIds(Iterable<Long> ids) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ProvinciaDTO> findAllActivasOrderByNombreAsc() {
        List<Provincia> provincias = repository.findAllByActivoTrueOrderByNombreAsc();
        return baseMapper.toDtoList(provincias);
    }

    @Override
    public List<ProvinciaDTO> findAllActivasByPaisIdOrderByNombreAsc(Long paisId) {
        List<Provincia> provincias = repository.findAllByActivoTrueAndPais_IdAndPais_ActivoTrueOrderByNombreAsc(paisId);
        return baseMapper.toDtoList(provincias);
    }

    @Override
    public List<ProvinciaDTO> findAllActivasByPaisNombreOrderByNombreAsc(String paisNombre) {
        List<Provincia> provincias = repository.findAllByActivoTrueAndPais_NombreAndPais_ActivoTrueOrderByNombreAsc(paisNombre);
        return baseMapper.toDtoList(provincias);
    }

    @Override
    protected Provincia updateEntityFromDto(Provincia entity, ProvinciaDTO entityDto) throws Exception {

        entity.setNombre(entityDto.getNombre());

        if (entityDto.getPaisId() != null) {
            PaisDTO paisDTO = paisService.findById(entityDto.getPaisId());
            //actualizo lista de provincias en pais
            paisDTO.getProvincias().add(entityDto);
            paisService.save(paisDTO);
            //pais en provincia
            entity.setPais(paisMapper.toEntity(paisDTO));

        }

        return entity;
    }

    @Override
    protected void validate(ProvinciaDTO entityDto) throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    protected void beforeSave(ProvinciaDTO entity) throws Exception {

        Optional<Provincia> opt = repository.findByActivoTrueAndNombreIgnoreCaseAndPais_IdAndPais_ActivoTrue(entity.getNombre(), entity.getPaisId());

        if (opt.isPresent()) {
            throw new Exception("La provincia ya existe");
        }

        if (entity.getPaisId() != null) {
            PaisDTO paisDTO = paisService.findById(entity.getPaisId());
            //actualizo lista de provincias en pais
            paisDTO.getProvincias().add(entity);
            paisService.save(paisDTO);

        }

    }

    @Override
    protected void afterSave(Provincia entity) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void afterUpdate(Provincia entity) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void beforeDelete(Provincia entity) throws Exception {
        if (entity.getDepartamentos() != null && !entity.getDepartamentos().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar la provincia porque tiene departamentos asociados");

        }

    }

    @Override
    protected void afterDelete(Provincia entity) throws Exception {
        // TODO Auto-generated method stub

    }

}
