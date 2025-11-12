package com.example.biblioteca.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.dao.BaseRestDao;
import com.example.biblioteca.dao.impl.AutorRestDaoImpl;
import com.example.biblioteca.domain.dto.AutorDTO;
import com.example.biblioteca.service.AutorService;

@Service
public class AutorServiceImpl extends BaseServiceImpl<AutorDTO, Long> implements AutorService{
	
	@Autowired
	private AutorRestDaoImpl daoAutor;
	
	public AutorServiceImpl(BaseRestDao<AutorDTO, Long> dao) {
		super(dao);
	}
	
	@Override
	protected void validar(AutorDTO autor) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/*public void crear(Long id, String nombre, String apellido, String biografia) throws Exception {
		try {
			
			validar(id, nombre, apellido, biografia);
			
			AutorDTO autorDto = new AutorDTO();
			autorDto.setId(id);
			autorDto.setNombre(nombre);
			autorDto.setApellido(apellido);
			autorDto.setBiografia(biografia);
			
			dao.crear(autorDto);
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	
	public void actualizar(Long id, String nombre, String apellido, String biografia) throws Exception {
		try {
			
			validar(id, nombre, apellido, biografia);
			
			AutorDTO autor = dao.buscarPorId(id);
			autor.setNombre(nombre);
			autor.setApellido(apellido);
			autor.setBiografia(biografia);
			
			dao.actualizar(autor);
			
		} catch (Exception e) {
			throw new Exception("Error al actualizar persona", e);
		}
	}*/
	
	@Override
	public List<AutorDTO> searchByNombreApellido(String filtro) throws Exception{
		
		try {
			return daoAutor.buscarPorNombreApellido(filtro);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public AutorDTO clonarAutor(Long idAutor) throws Exception {
		try {
	        return daoAutor.clonar(idAutor);
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception("Error al clonar autor", e);
	    }
	}



	
	

}
