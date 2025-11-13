package com.projects.mycar.mycar_server.services.impl;

import com.projects.mycar.mycar_server.dto.BaseDTO;
import com.projects.mycar.mycar_server.entities.Base;
import com.projects.mycar.mycar_server.repositories.BaseRepository;
import com.projects.mycar.mycar_server.services.BaseService;
import com.projects.mycar.mycar_server.services.mapper.BaseMapper;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<E extends Base, D extends BaseDTO, ID extends Serializable> implements BaseService<D, ID> {

    protected BaseRepository<E, ID> baseRepository;
    protected BaseMapper<E, D> baseMapper;

    public BaseServiceImpl(BaseRepository<E, ID> baseRepository, BaseMapper<E, D> baseMapper) {
        this.baseRepository = baseRepository;
        this.baseMapper = baseMapper;
    }

    @Override
    @Transactional
    public List<D> findAll() throws Exception {
        try {
            List<E> entities = Optional.ofNullable(baseRepository.findByActivoTrue())
                    .orElse(Collections.emptyList());
            if (entities.isEmpty()) {
                System.out.println("LA LISTA ESTA VACIA");
            } else {
                System.out.println("LISTA NO VACIA");
            }


            return baseMapper.toDtoList(entities);
        } catch (Exception e) {
            throw new Exception("Error al obtener entidades", e);
        }
    }

    @Override
    @Transactional
    public D findById(ID id) throws Exception {
        try {

            Optional<E> opt = baseRepository.findByIdAndActivoTrue(id);

            if (opt.isPresent()) {
                return baseMapper.toDto(opt.get());
            }

            return null;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public D save(D dto) throws Exception {
        validate(dto);
        beforeSave(dto);
        E entity = baseMapper.toEntity(dto);
        saveToRepository(entity);
        afterSave(entity);

        return baseMapper.toDto(entity);
    }

    @Override
    @Transactional
    public D update(ID id, D dto) throws Exception {
        E entity = convertToEntity(dto);
        validate(dto);
        beforeUpdate(dto);
        E updated = updateEntityFromDto(entity, dto);
        saveToRepository(updated);
        afterUpdate(updated);

        return baseMapper.toDto(updated);
    }

    @Override
    @Transactional
    public boolean delete(ID id) throws Exception {
        D dto = findById(id);

        if (dto == null) {
            throw new Exception("La entidad que desea eliminar no existe");
        }

        E entity = convertToEntity(dto);
        beforeDelete(entity);
        entity.setActivo(false);
        saveToRepository(entity);
        afterDelete(entity);
        return true;

    }

    @Transactional
    protected E convertToEntity(D dto) throws Exception {
        return baseRepository.findByIdAndActivoTrue((ID) dto.getId())
                .orElseThrow(() -> new Exception("No se encontró la entidad con id " + dto.getId()));
    }

    @Transactional
    public boolean existsById(ID id) {
        Optional<E> opt = baseRepository.findByIdAndActivoTrue(id);
        return opt.isPresent();
    }

    // IMPLEMENTAR ESTOS ---------------------

    protected void saveToRepository(E entity) throws Exception {
        baseRepository.save(entity);
    }

    protected abstract E updateEntityFromDto(E entity, D entityDto) throws Exception;

    protected abstract void validate(D entityDto) throws Exception;
    //----------------------------------------

    protected abstract void beforeSave(D entity) throws Exception;

    protected abstract void afterSave(E entity) throws Exception;

    protected void beforeUpdate(D entity) throws Exception {
        if (!existsById((ID) entity.getId())) {
            throw new Exception("La entidad que desea modificar no existe");
        }
    }

    protected abstract void afterUpdate(E entity) throws Exception;

    protected abstract void beforeDelete(E entity) throws Exception;

    protected abstract void afterDelete(E entity) throws Exception;


}
