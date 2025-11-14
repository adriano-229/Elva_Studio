package com.projects.mycar.mycar_admin.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mycar.mycar_admin.domain.AlquilerDTO;
import com.example.mycar.mycar_admin.domain.DocumentacionDTO;
import com.projects.mycar.mycar_admin.dao.BaseRestDao;
import com.projects.mycar.mycar_admin.dao.impl.AlquilerRestDaoImpl;
import com.projects.mycar.mycar_admin.service.AlquilerService;

@Service
public class AlquilerServiceImpl extends BaseServiceImpl<AlquilerDTO, Long> implements AlquilerService{
	
	@Autowired
	private AlquilerRestDaoImpl daoAlquiler;
	
	@Autowired
	private DocumentacionServiceImpl documentacionService;
	
	public AlquilerServiceImpl(BaseRestDao<AlquilerDTO, Long> dao) {
		super(dao);
	}
	
	@Override
	public List<AlquilerDTO> buscarPorPeriodo(LocalDate desde, LocalDate hasta) throws Exception {
		try {
			return daoAlquiler.buscarPorPeriodo(desde, hasta);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<AlquilerDTO> buscarPorCliente(Long idCliente) throws Exception {
		try {
			return daoAlquiler.buscarPorCliente(idCliente);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	protected void validar(AlquilerDTO entity) throws Exception {
		
	}

	@Override
	protected void beforeSave(AlquilerDTO entity) throws Exception {
		try {
			DocumentacionDTO documentacion = documentacionService.saveDocumentacion(entity.getDocumentacion());
	        entity.setDocumentacionId(documentacion.getId());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
    }

}
