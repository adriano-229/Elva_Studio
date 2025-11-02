package com.example.contactosApp.service.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.example.contactosApp.domain.Base;
import com.example.contactosApp.domain.Empresa;
import com.example.contactosApp.domain.dto.BaseDTO;
import com.example.contactosApp.repository.BaseRepository;
import com.example.contactosApp.service.BaseService;
import com.example.contactosApp.service.mapper.BaseMapper;

import jakarta.transaction.Transactional;

public abstract class BaseServiceImpl<E extends Base, D extends BaseDTO, ID extends Serializable> implements BaseService<D, ID>{

	protected BaseRepository<E, ID> baseRepository;
	protected BaseMapper<E, D> baseMapper;
	
	
	public BaseServiceImpl(BaseRepository<E, ID> baseRepository, BaseMapper<E, D> baseMapper) {
		this.baseRepository = baseRepository;
		this.baseMapper = baseMapper;}
	
	@Override
	@Transactional
	public List<D> findAll() throws Exception {
	    try {
	        List<E> entities = Optional.ofNullable(baseRepository.findByActivoTrue())
	                                   .orElse(Collections.emptyList());
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
	public void delete(ID id) throws Exception {
		D dto = findById(id);
		System.out.println("empresa en el delete: " + dto);
		if (dto == null) {
			throw new Exception("La entidad que desea eliminar no existe");
		}
		
		E entity = convertToEntity(dto);
		beforeDelete(entity);
		entity.setActivo(false);
		saveToRepository(entity);
        afterDelete(entity);
        
    }
	
	@Transactional
	protected E convertToEntity(D dto) throws Exception{
		return baseRepository.findByIdAndActivoTrue((ID) dto.getId())
				.orElseThrow(() -> new Exception("No se encontró la entidad con id " + dto.getId()));
	}
	
	@Transactional
	public boolean existsById(ID id) {
		Optional<E> opt = baseRepository.findByIdAndActivoTrue(id);
		if (opt.isEmpty()) return false;
		return true;
	}

	// IMPLEMENTAR ESTOS ---------------------
    
    protected void saveToRepository(E entity) throws Exception{
    	baseRepository.save(entity);
    }
    protected abstract E updateEntityFromDto(E entity, D entityDto) throws Exception;
    protected abstract void validate(D entityDto) throws Exception;
    //----------------------------------------

    protected abstract void beforeSave(D entity) throws Exception;
    protected abstract void afterSave(E entity) throws Exception;
    
    protected void beforeUpdate(D entity) throws Exception{
    	if (!existsById((ID) entity.getId())) {
			throw new Exception("La entidad que desea modificar no existe");
		}
    }
    protected abstract void afterUpdate(E entity) throws Exception;
    
    protected abstract void beforeDelete(E entity) throws Exception;
    protected abstract void afterDelete(E entity) throws Exception;
	
	
	
	
	

}
