package com.projects.mycar.mycar_cliente.service.impl;

import java.io.Serializable;
import java.util.List;

import com.projects.mycar.mycar_cliente.dao.BaseRestDao;
import com.projects.mycar.mycar_cliente.domain.BaseDTO;
import com.projects.mycar.mycar_cliente.service.BaseService;


public abstract class BaseServiceImpl<E extends BaseDTO, ID extends Serializable> implements BaseService<E, ID> {
	
	protected BaseRestDao<E, ID> dao;
	
	public BaseServiceImpl(BaseRestDao<E, ID> dao) {
		this.dao = dao;
	}
	
	protected abstract void validar(E entity) throws Exception;
	
	@Override
	public List<E> findAll() throws Exception {
		try {
			List<E> entities = dao.listarActivo();
			return entities;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/*@Override
	public Page<E> findAll(Pageable pageable) throws Exception{
		try {
			Page<E> entities = baseRepository.findAll(pageable);
			return entities;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}*/

	@Override
	public E findById(ID id) throws Exception {
		try {
			return dao.buscarPorId(id)
	                  .orElseThrow(() -> new Exception("Entidad no encontrada"));
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	public void save(E entity) throws Exception {
		try {
			validar(entity);
			dao.crear(entity);
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public void update(ID id, E entity) throws Exception {
		try {
			validar(entity);
			dao.buscarPorId(id)
				.orElseThrow(() -> new RuntimeException("Entidad no encontrada"));
			
			entity.setId((Long) id);
			
			dao.actualizar(entity);
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public void delete(ID id) throws Exception {
		try {
			
			if (dao.existsById(id)) {
				dao.eliminar(id);
				
			} else {
				throw new Exception("La entidad que desea eliminar no existe");
			}
			
		} catch (Exception e) {
			throw e;
		}
	}

}
