package com.projects.gym.gym_app.service.mapper;

import com.projects.gym.gym_app.domain.DetalleFactura;
import com.projects.gym.gym_app.domain.Factura;
import com.projects.gym.gym_app.domain.FormaDePago;
import com.projects.gym.gym_app.domain.Socio;
import com.projects.gym.gym_app.service.dto.DetalleFacturaDTO;
import com.projects.gym.gym_app.service.dto.FacturaDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Centraliza el mapeo entre la entidad {@link Factura} y su representación {@link FacturaDTO}.
 * De esta forma evitamos duplicar lógica en los distintos servicios que exponen facturas.
 */
public final class FacturaMapper {

    private FacturaMapper() {
    }

    public static FacturaDTO toDto(Factura factura) {
        BigDecimal totalPagado = Optional.ofNullable(factura.getTotalPagado()).orElse(BigDecimal.ZERO);
        List<DetalleFacturaDTO> detalles = Optional.ofNullable(factura.getDetalles()).orElse(List.of())
                .stream()
                .map(FacturaMapper::mapDetalle)
                .toList();

        Socio socio = factura.getSocio();
        FormaDePago forma = factura.getFormaDePago();
        Long socioId = socio != null ? socio.getId() : null;
        String socioNombre = socio != null ? socio.getApellido() + ", " + socio.getNombre() : "";
        String formaId = forma != null ? forma.getId() : null;
        String formaTexto = forma != null ? forma.getTipoPago().name() : null;

        return new FacturaDTO(
                factura.getId(),
                factura.getNumeroFactura(),
                factura.getFechaFactura(),
                totalPagado,
                factura.getEstado(),
                socioId,
                socioNombre,
                formaId,
                formaTexto,
                detalles
        );
    }

    private static DetalleFacturaDTO mapDetalle(DetalleFactura detalle) {
        var cuota = detalle.getCuotaMensual();
        String cuotaId = cuota != null ? cuota.getId() : null;
        String mes = cuota != null ? cuota.getMes() : null;
        Integer anio = cuota != null ? cuota.getAnio() : null;
        BigDecimal importe = Optional.ofNullable(detalle.getImporte()).orElse(BigDecimal.ZERO);

        return new DetalleFacturaDTO(cuotaId, mes, anio, importe);
    }
}
