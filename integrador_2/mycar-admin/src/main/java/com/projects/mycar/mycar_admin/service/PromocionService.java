package com.projects.mycar.mycar_admin.service;

import com.example.mycar.mycar_admin.domain.ConfiguracionPromocionDTO;

public interface PromocionService extends BaseService<ConfiguracionPromocionDTO, Long>{
	
	ConfiguracionPromocionDTO configurarPromocion(ConfiguracionPromocionDTO configuracion) throws Exception;
	ConfiguracionPromocionDTO obtenerConfiguracionActiva() throws Exception;
	String enviarPromocionesManual() throws Exception;

}
