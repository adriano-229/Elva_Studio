package com.projects.mycar.mycar_admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mycar.mycar_admin.domain.AlquilerFormDTO;
import com.projects.mycar.mycar_admin.dao.impl.CostoRestDaoImpl;
import com.projects.mycar.mycar_admin.service.CostoService;

@Service
public class CostoServiceImpl implements CostoService{
	
	@Autowired
	private CostoRestDaoImpl daoCosto;

	@Override
	public AlquilerFormDTO cotizarAlquiler(AlquilerFormDTO alquiler) throws Exception {
		
		try {
			return daoCosto.calcularCostoAlquiler(alquiler);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

}
