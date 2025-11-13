package com.projects.mycar.mycar_server.services;

import com.projects.mycar.mycar_server.dto.FacturaDTO;
import com.projects.mycar.mycar_server.dto.RespuestaPagoDTO;
import com.projects.mycar.mycar_server.dto.SolicitudPagoDTO;

import java.util.List;

/**
 * Servicio para gestionar pagos y facturas.
 */
public interface PagoService {

    /**
     * Procesa una solicitud de pago.
     * Genera una factura pendiente de aprobación.
     *
     * @param solicitud Datos del pago
     * @return Respuesta con información del pago/factura
     */
    RespuestaPagoDTO procesarPago(SolicitudPagoDTO solicitud) throws Exception;

    /**
     * Obtiene todas las facturas pendientes de aprobación.
     *
     * @return Lista de facturas pendientes
     */
    List<FacturaDTO> obtenerFacturasPendientes() throws Exception;

    /**
     * Aprueba una factura (cambia estado a PAGADA).
     *
     * @param facturaId ID de la factura
     * @return Factura aprobada
     */
    FacturaDTO aprobarFactura(Long facturaId) throws Exception;

    /**
     * Rechaza/anula una factura.
     *
     * @param facturaId ID de la factura
     * @param motivo    Motivo de anulación
     * @return Factura anulada
     */
    FacturaDTO anularFactura(Long facturaId, String motivo) throws Exception;
}

