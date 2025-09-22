package com.projects.gym.gym_app.service;

import com.projects.gym.gym_app.service.dto.*;
import org.springframework.data.domain.*;

public interface FacturaService {
    FacturaDTO crearFactura(EmisionFacturaCommand cmd);
    FacturaDTO buscarPorId(String id);
    Page<FacturaDTO> listar(String socioId, String estado, String desde, String hasta, Pageable pageable);
    void anular(String facturaId, String motivo);
}
