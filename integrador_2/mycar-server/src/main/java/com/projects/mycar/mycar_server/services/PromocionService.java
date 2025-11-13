package com.projects.mycar.mycar_server.services;

import com.projects.mycar.mycar_server.dto.CodigoDescuentoDTO;
import com.projects.mycar.mycar_server.dto.ConfiguracionPromocionDTO;

public interface PromocionService {
    ConfiguracionPromocionDTO crearOActualizarConfiguracion(ConfiguracionPromocionDTO configuracionDTO);

    ConfiguracionPromocionDTO obtenerConfiguracionActiva();

    void generarYEnviarPromocionesAutomaticas();

    CodigoDescuentoDTO validarYObtenerCodigoDescuento(String codigo, Long clienteId);
}

