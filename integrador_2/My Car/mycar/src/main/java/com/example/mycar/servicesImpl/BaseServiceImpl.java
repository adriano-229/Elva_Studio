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
	@Transactional //hacen transacciones con la base de datos
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
	
}