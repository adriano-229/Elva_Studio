package com.example.biblioteca.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.dao.BaseRestDao;
import com.example.biblioteca.dao.impl.DomicilioRestDaoImpl;
import com.example.biblioteca.domain.dto.DomicilioDTO;
import com.example.biblioteca.service.DomicilioService;

@Service
public class DomicilioServiceImpl extends BaseServiceImpl<DomicilioDTO, Long> implements DomicilioService{

	@Autowired
	private DomicilioRestDaoImpl daoDomicilio;
	
	public DomicilioServiceImpl(BaseRestDao<DomicilioDTO, Long> dao) {
		super(dao);
	}
	
	@Override
	protected void validar(DomicilioDTO domicilio) throws Exception {
		
	}
	

	@Override
	public DomicilioDTO buscarPorCalleNroYLocalidad(String calle, int numero, String denominacion) throws Exception {
		
		try {
			return daoDomicilio.buscarPorCalleNumeroYLocalidad(calle, numero, denominacion);
			 
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	
	}

	
	

}
