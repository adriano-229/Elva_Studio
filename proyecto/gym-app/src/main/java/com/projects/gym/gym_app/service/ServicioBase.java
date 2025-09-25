package com.projects.gym.gym_app.service;

import java.util.List;

public interface ServicioBase<E, S> {
	
	E guardar(E entity) throws Exception;
	E actualizar(E entity, String id) throws Exception;
	boolean eliminarPorId(String id) throws Exception;
	E buscarPorId(String id) throws Exception;
	List<E> listarTodos() throws Exception;
	List<E> listarActivos(S entity) throws Exception;
	
	
	
}
