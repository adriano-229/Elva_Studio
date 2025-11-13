package com.example.mycar.services;

import com.example.mycar.dto.CodigoDescuentoDTO;
import com.example.mycar.dto.ConfiguracionPromocionDTO;

public interface PromocionService {
    ConfiguracionPromocionDTO crearOActualizarConfiguracion(ConfiguracionPromocionDTO configuracionDTO);

    ConfiguracionPromocionDTO obtenerConfiguracionActiva();

    void generarYEnviarPromocionesAutomaticas();

    CodigoDescuentoDTO validarYObtenerCodigoDescuento(String codigo, Long clienteId);
}

