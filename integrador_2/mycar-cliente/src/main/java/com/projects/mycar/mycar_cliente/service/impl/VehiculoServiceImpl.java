package com.projects.mycar.mycar_cliente.service.impl;

import com.projects.mycar.mycar_cliente.dao.BaseRestDao;
import com.projects.mycar.mycar_cliente.dao.VehiculoRestDao;
import com.projects.mycar.mycar_cliente.domain.VehiculoDTO;
import com.projects.mycar.mycar_cliente.domain.enums.EstadoVehiculo;
import com.projects.mycar.mycar_cliente.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VehiculoServiceImpl extends BaseServiceImpl<VehiculoDTO, Long> implements VehiculoService {

    @Autowired
    private VehiculoRestDao daoVehiculo;

    public VehiculoServiceImpl(BaseRestDao<VehiculoDTO, Long> dao) {
        super(dao);
    }

    @Override
    public List<VehiculoDTO> buscarPorEstado(EstadoVehiculo estado) {
        try {
            return daoVehiculo.findByEstado(estado);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }

    }
	
	/*
	@Override
	public List<VehiculoDTO> findByEstadoVehiculoAndActivoTrue(EstadoVehiculo estado) {
		try {
			return daoVehiculo.findByEstadoVehiculoAndActivoTrue(estado);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/

    @Override
    protected void validar(VehiculoDTO entity) throws Exception {

    }


}
