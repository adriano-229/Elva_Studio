package com.projects.gym.gym_app.service;
import com.projects.gym.gym_app.domain.Factura;
import com.projects.gym.gym_app.service.dto.CuotaPendienteDTO;
import com.projects.gym.gym_app.service.dto.EmisionFacturaCommand;
import com.projects.gym.gym_app.service.dto.FacturaDTO;
import com.projects.gym.gym_app.service.dto.PagoCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FacturaService {
    FacturaDTO crearFactura(EmisionFacturaCommand cmd);
    FacturaDTO buscarPorId(String id);
    Page<FacturaDTO> listar(String socioId, String estado, String desde, String hasta, Pageable pageable);
    List<Factura> listarPagadas(Long socioId) throws Exception;
    FacturaDTO confirmarPagoManual(String facturaId, PagoCommand cmd);
    FacturaDTO anularFactura(String facturaId, String observacion);
    List<CuotaPendienteDTO> cuotasPendientes(Long socioId);
}
