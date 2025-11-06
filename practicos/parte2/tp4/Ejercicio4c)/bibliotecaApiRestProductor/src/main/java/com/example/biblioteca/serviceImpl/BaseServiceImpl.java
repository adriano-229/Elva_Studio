package com.example.biblioteca.serviceImpl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.biblioteca.entities.Base;
import com.example.biblioteca.repository.BaseRepository;
import com.example.biblioteca.service.BaseService;

import jakarta.transaction.Transactional;

public abstract class BaseServiceImpl<E extends Base, ID extends Serializable> implements BaseService<E, ID> {
	
	protected BaseRepository<E, ID> baseRepository;
	
	public BaseServiceImpl(BaseRepository<E, ID> baseRepository) {
		this.baseRepository = baseRepository;
	}
	
	@Override
	@Transactional //hacen transacciones con la base de datos
	public List<E> findAll() throws Exception {
		try {
			List<E> entities = baseRepository.findByActivoTrue();
			return entities;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/*@Override
	@Transactional 
	public List<E> findAllByIds(Iterable<ID> ids) throws Exception {
		try {
			List<E> entities = baseRepository.findAllById(ids);
			return entities;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}*/
	
	
	@Override
	@Transactional
	public Page<E> findAll(Pageable pageable) throws Exception{
		try {
			Page<E> entities = baseRepository.findAll(pageable);
			return entities;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional
	public E findByid(ID id) throws Exception {
		try {
			Optional<E> opt = baseRepository.findByIdAndActivoTrue(id);
			return opt.get();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	//IMPLEMENTARLO PROPIAMENTE EN CADA CLASE Y APARTIR DE ENTITY HACER LAS VALIDACIONES Y CREAR EL OBJETO 
	
	/*@Override
	@Transactional
	public E save(E entity) throws Exception {
		try {
			
			entity = baseRepository.save(entity);
			return entity;
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}*/

	/*@Override
	@Transactional
	public E update(ID id, E entity) throws Exception {
		try {
			
			Optional<E> opt = baseRepository.findByIdAndActivoTrue(id);
			E entityUpdate = opt.get();
			//si no se obtiene la persona hace un rollback y lanza la excepcion
			entityUpdate = baseRepository.save(entity);
			return entityUpdate;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}*/

	/*@Override
	@Transactional
	public boolean delete(ID id) throws Exception {
		try {
			
			if (baseRepository.existsById(id)) {
				//baseRepository.deleteById(id);
				Optional<E> opt = baseRepository.findByIdAndActivoTrue(id);
				E entity = opt.get();
				
				entity.setActivo(false);
				
				return true;
			} else {
				throw new Exception();
			}
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}*/

}
