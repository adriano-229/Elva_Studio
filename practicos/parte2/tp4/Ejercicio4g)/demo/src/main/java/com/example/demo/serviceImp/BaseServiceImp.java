package com.example.demo.serviceImp;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;

import com.example.demo.entities.Base;
import com.example.demo.entities.DetalleHistoriaClinica;
import com.example.demo.repositories.BaseRepository;
import com.example.demo.services.BaseService;

import jakarta.transaction.Transactional;

public abstract class BaseServiceImp<E extends Base, ID extends Serializable> implements BaseService<E, ID> {
	
	protected BaseRepository<E, ID> baseRepository;
	
	public BaseServiceImp(BaseRepository<E, ID> baseRepository) {
		this.baseRepository = baseRepository;
	}
	
	@Override
	@Transactional
	public List<E> findAll() throws Exception {
		try {
			List<E> entities = baseRepository.findByEliminadoFalse();
			return entities;
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional
	public E findByid(ID id) throws Exception {
		try {
			Optional<E> entity = baseRepository.findById(id);
			return entity.get();
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional
	public E save(E entity) throws Exception {
		try {
			return baseRepository.save(entity);
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional
	public E update(ID id, E entity) throws Exception {
		try {
			Optional<E> respuesta = baseRepository.findById(id);
			if (respuesta.isPresent()) {
				E entityToUpdate = respuesta.get();
				//actualizo los datos con beanutils
				BeanUtils.copyProperties(entity, entityToUpdate, "id");
				return baseRepository.save(entityToUpdate);
			} else {
				return null;
			}
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}		
	}

	@Override
	@Transactional
	public boolean delete(ID id) throws Exception {
		try {
			if (baseRepository.existsById(id)) {
				baseRepository.deleteById(id);
				return true;
			} else {
				return false;
			}
			
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
