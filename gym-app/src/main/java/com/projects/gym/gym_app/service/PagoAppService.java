package com.projects.gym.gym_app.service;

import com.projects.gym.gym_app.service.dto.PagoCommand;
import com.projects.gym.gym_app.service.dto.FacturaDTO;

public interface PagoAppService {
    FacturaDTO pagarFactura(String facturaId, PagoCommand cmd);
}
