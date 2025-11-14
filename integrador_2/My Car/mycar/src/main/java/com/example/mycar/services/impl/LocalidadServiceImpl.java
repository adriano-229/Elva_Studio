package com.example.mycar.services.impl;

import com.example.mycar.dto.DepartamentoDTO;
import com.example.mycar.dto.LocalidadDTO;
import com.example.mycar.entities.Localidad;
import com.example.mycar.repositories.BaseRepository;
import com.example.mycar.repositories.LocalidadRepository;
import com.example.mycar.services.LocalidadService;
import com.example.mycar.services.mapper.BaseMapper;
import com.example.mycar.services.mapper.DepartamentoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocalidadServiceImpl extends BaseServiceImpl<Localidad, LocalidadDTO, Long> implements LocalidadService {

    @Autowired
    private LocalidadRepository repository;

    @Autowired
    private DepartamentoMapper departamentoMapper;

    @Autowired
    private DepartamentoServiceImpl departamentoService;

    public LocalidadServiceImpl(BaseRepository<Localidad, Long> baseRepository,
                                BaseMapper<Localidad, LocalidadDTO> baseMapper) {
        super(baseRepository, baseMapper);
    }

    @Override
    public List<LocalidadDTO> findAllByIds(Iterable<Long> ids) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LocalidadDTO> findByDepartamentoId(Long departamentoId) {
        List<Localidad> localidades = repository.findAllByActivoTrueAndDepartamento_IdAndDepartamento_ActivoTrueOrderByNombreAsc(departamentoId);
        return baseMapper.toDtoList(localidades);
    }

    @Override
    public List<LocalidadDTO> findByDepartamentoNombre(String nombre) {
        List<Localidad> localidades = repository.findAllByActivoTrueAndDepartamento_NombreAndDepartamento_ActivoTrueOrderByNombreAsc(nombre);
        return baseMapper.toDtoList(localidades);
    }

    @Override
    public Optional<LocalidadDTO> findByNombreYDepartamento(String nombre, Long departamentoId) {
        Optional<Localidad> opt = repository.findByActivoTrueAndNombreIgnoreCaseAndDepartamento_IdAndDepartamento_ActivoTrue(nombre, departamentoId);

        return Optional.ofNullable(baseMapper.toDto(opt.get()));

    }

    @Override
    protected Localidad updateEntityFromDto(Localidad entity, LocalidadDTO entityDto) throws Exception {
        entity.setCodigoPostal(entityDto.getCodigoPostal());
        entity.setNombre(entityDto.getNombre());

        if (entityDto.getDepartamentoId() != null) {
            DepartamentoDTO deptoDTO = departamentoService.findById(entityDto.getDepartamentoId());
            //depto en localidad
            entity.setDepartamento(departamentoMapper.toEntity(deptoDTO));
            //actualizo lista de localidades en depto
            deptoDTO.getLocalidades().add(entityDto);
            departamentoService.save(deptoDTO);

        }


        return entity;
    }

    @Override
    protected void validate(LocalidadDTO entityDto) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void beforeSave(LocalidadDTO entity) throws Exception {
        Optional<Localidad> opt = repository.findByActivoTrueAndNombreIgnoreCaseAndDepartamento_IdAndDepartamento_ActivoTrue(entity.getNombre(), entity.getDepartamentoId());
        if (opt.isPresent()) {
            throw new Exception("La localidad ya existe");
        }

        if (entity.getDepartamentoId() != null) {
            DepartamentoDTO deptoDTO = departamentoService.findById(entity.getDepartamentoId());
            //actualizo lista de localidades en depto
            deptoDTO.getLocalidades().add(entity);
            departamentoService.save(deptoDTO);
        }


    }

    @Override
    protected void afterSave(Localidad entity) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void afterUpdate(Localidad entity) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void beforeDelete(Localidad entity) throws Exception {
        if (entity.getDirecciones() != null && !entity.getDirecciones().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar la localidad porque tiene direcciones asociadas");
        }

    }

    @Override
    protected void afterDelete(Localidad entity) throws Exception {
        // TODO Auto-generated method stub

    }

}
