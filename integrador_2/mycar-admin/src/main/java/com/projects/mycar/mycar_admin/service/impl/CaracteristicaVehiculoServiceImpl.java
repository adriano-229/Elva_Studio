package com.projects.mycar.mycar_admin.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.mycar.mycar_admin.domain.CaracteristicaVehiculoDTO;
import com.example.mycar.mycar_admin.domain.VehiculoDTO;
import com.projects.mycar.mycar_admin.dao.BaseRestDao;
import com.projects.mycar.mycar_admin.dao.impl.CaracteristicaVehiculoRestDaoImpl;
import com.projects.mycar.mycar_admin.service.CaracteristicaVehiculoService;

@Service
public class CaracteristicaVehiculoServiceImpl extends BaseServiceImpl<CaracteristicaVehiculoDTO, Long> implements CaracteristicaVehiculoService {

	@Autowired
	private CaracteristicaVehiculoRestDaoImpl daoCaracteristica;
	
	public CaracteristicaVehiculoServiceImpl(BaseRestDao<CaracteristicaVehiculoDTO, Long> dao) {
		super(dao);
	}
	
	 public CaracteristicaVehiculoDTO guardarCaracteristica(CaracteristicaVehiculoDTO dto) throws Exception {
	        try {
	            validar(dto);
	            beforeSave(dto);
	            return daoCaracteristica.crearCaracteristica(dto);

	        } catch (Exception e) {
	            throw new Exception(e.getMessage());
	        }
	    }
	 
	 public CaracteristicaVehiculoDTO actualizarCaracteristica(CaracteristicaVehiculoDTO dto) throws Exception {
	        try {
	            validar(dto);
	            beforeSave(dto);
	            return daoCaracteristica.actualizarCaracteristica(dto.getId(), dto);

	        } catch (Exception e) {
	            throw new Exception(e.getMessage());
	        }
	    }

	@Override
	protected void validar(CaracteristicaVehiculoDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeSave(CaracteristicaVehiculoDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public Optional<CaracteristicaVehiculoDTO> buscarPorId(Long id) throws Exception {
        return daoCaracteristica.buscarPorId(id);
    }



	
}
