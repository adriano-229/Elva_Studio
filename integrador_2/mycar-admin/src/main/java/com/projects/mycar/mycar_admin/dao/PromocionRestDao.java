package com.projects.mycar.mycar_admin.dao;

import com.example.mycar.mycar_admin.domain.ConfiguracionPromocionDTO;

public interface PromocionRestDao extends BaseRestDao<ConfiguracionPromocionDTO, Long> {

    ConfiguracionPromocionDTO configurarPromocion(ConfiguracionPromocionDTO configuracion) throws Exception;

    ConfiguracionPromocionDTO obtenerConfiguracionActiva() throws Exception;

    String enviarPromocionesManual() throws Exception;
}
