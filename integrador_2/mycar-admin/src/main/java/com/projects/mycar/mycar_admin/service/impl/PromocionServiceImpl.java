package com.projects.mycar.mycar_admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mycar.mycar_admin.domain.ConfiguracionPromocionDTO;
import com.projects.mycar.mycar_admin.dao.BaseRestDao;
import com.projects.mycar.mycar_admin.dao.impl.PromocionRestDaoImpl;
import com.projects.mycar.mycar_admin.service.PromocionService;

@Service
public class PromocionServiceImpl extends BaseServiceImpl<ConfiguracionPromocionDTO, Long> implements PromocionService{
	
	@Autowired
	private PromocionRestDaoImpl daoPromocion;
	
	
	public PromocionServiceImpl(BaseRestDao<ConfiguracionPromocionDTO, Long> dao) {
		super(dao);
	}

	@Override
	public ConfiguracionPromocionDTO configurarPromocion(ConfiguracionPromocionDTO configuracion) throws Exception {
		try {
			return daoPromocion.configurarPromocion(configuracion);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public ConfiguracionPromocionDTO obtenerConfiguracionActiva() throws Exception {
		try {
			return daoPromocion.obtenerConfiguracionActiva();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public String enviarPromocionesManual() throws Exception {
		try {
			return daoPromocion.enviarPromocionesManual();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	protected void validar(ConfiguracionPromocionDTO entity) throws Exception {
		
	}

}
