package com.projects.mycar.mycar_server.services.impl;

import com.projects.mycar.mycar_server.dto.DepartamentoDTO;
import com.projects.mycar.mycar_server.dto.ProvinciaDTO;
import com.projects.mycar.mycar_server.entities.Departamento;
import com.projects.mycar.mycar_server.repositories.BaseRepository;
import com.projects.mycar.mycar_server.repositories.DepartamentoRepository;
import com.projects.mycar.mycar_server.services.DepartamentoService;
import com.projects.mycar.mycar_server.services.mapper.BaseMapper;
import com.projects.mycar.mycar_server.services.mapper.ProvinciaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoServiceImpl extends BaseServiceImpl<Departamento, DepartamentoDTO, Long> implements DepartamentoService {

    @Autowired
    private DepartamentoRepository repository;

    @Autowired
    private ProvinciaServiceImpl provinciaService;

    @Autowired
    private ProvinciaMapper provinciaMapper;

    public DepartamentoServiceImpl(BaseRepository<Departamento, Long> baseRepository,
                                   BaseMapper<Departamento, DepartamentoDTO> baseMapper) {
        super(baseRepository, baseMapper);
    }

    @Override
    public List<DepartamentoDTO> findAllByIds(Iterable<Long> ids) throws Exception {
        return null;
    }

    @Override
    public List<DepartamentoDTO> findAllByOrderByNombreAsc() {
        List<Departamento> departamentos = repository.findAllByActivoTrueOrderByNombreAsc();
        return baseMapper.toDtoList(departamentos);
    }

    @Override
    public List<DepartamentoDTO> findAllActivosByProvinciaIdOrderByNombre(Long provinciaId) {
        List<Departamento> departamentos = repository.findAllByActivoTrueAndProvincia_IdOrderByNombre(provinciaId);
        return baseMapper.toDtoList(departamentos);
    }

    @Override
    public List<DepartamentoDTO> findAllActivosByProvinciaNombreOrderByNombre(String provinciaNombre) {
        List<Departamento> departamentos = repository.findAllByActivoTrueAndProvincia_NombreOrderByNombre(provinciaNombre);
        return baseMapper.toDtoList(departamentos);
    }

    @Override
    protected Departamento updateEntityFromDto(Departamento entity, DepartamentoDTO entityDto) throws Exception {
        entity.setNombre(entityDto.getNombre());

        if (entityDto.getProvinciaId() != null) {
            ProvinciaDTO provDTO = provinciaService.findById(entityDto.getProvinciaId());
            //actualizo lista de localidades en depto
            provDTO.getDepartamentos().add(entityDto);
            provinciaService.save(provDTO);
            //depto en localidad
            entity.setProvincia(provinciaMapper.toEntity(provDTO));

        }

        return entity;
    }

    @Override
    protected void validate(DepartamentoDTO entityDto) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void beforeSave(DepartamentoDTO entity) throws Exception {
        Optional<Departamento> opt = repository.findByActivoTrueAndNombreIgnoreCaseAndProvincia_IdAndProvincia_ActivoTrue(entity.getNombre(), entity.getProvinciaId());

        if (opt.isPresent()) {
            throw new Exception("El Departamento ya existe");
        }

        if (entity.getProvinciaId() != null) {
            ProvinciaDTO provDTO = provinciaService.findById(entity.getProvinciaId());
            //actualizo lista de departamentos en prov
            provDTO.getDepartamentos().add(entity);
            provinciaService.save(provDTO);

        }
    }

    @Override
    protected void afterSave(Departamento entity) throws Exception {


    }

    @Override
    protected void afterUpdate(Departamento entity) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void beforeDelete(Departamento departamento) throws Exception {
        if (departamento.getLocalidades() != null && !departamento.getLocalidades().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar el departamento porque tiene localidades asociadas");
        }


    }

    @Override
    protected void afterDelete(Departamento entity) throws Exception {
        // TODO Auto-generated method stub

    }

}
