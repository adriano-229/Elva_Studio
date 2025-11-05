package com.example.mycar.servicesImpl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.example.mycar.entities.Base;
import com.example.mycar.repositories.BaseRepository;
import com.example.mycar.services.BaseService;

import jakarta.transaction.Transactional;


public abstract class BaseServiceImpl<E extends Base, ID extends Serializable> implements BaseService<E, ID> {
	
	protected BaseRepository<E, ID> baseRepository;
	
	public BaseServiceImpl(BaseRepository<E, ID> baseRepository) {
		this.baseRepository = baseRepository;
	}
	
	@Override
	@Transactional 
	public List<E> findAll() throws Exception {
		try {
			List<E> entities = baseRepository.findByActivoTrue();
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
	
	@Override
	@Transactional 
	public List<E> findAllByIds(Iterable<ID> ids) throws Exception {
		try {
			List<E> entities = baseRepository.findAllById(ids);
			return entities;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public boolean delete(ID id) throws Exception{
		try {
			if (baseRepository.existsById(id)) {
				Optional<E> opt = baseRepository.findById(id);
				E entity = opt.get();
				
				entity.setActivo(false);
				return true;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}