package elva.studio.services;

import java.util.List;

public interface ServicioBase<E, S> {
	
	E guardar(E entity) throws Exception;
	E actualizar(E entity, Long id) throws Exception;
	boolean eliminarPorId(Long id) throws Exception;
	E buscarPorId(Long id) throws Exception;
	List<E> listarTodos() throws Exception;
	List<E> listarActivos(S entity) throws Exception;
	
	
	
}
