package com.example.mycar.services.impl;

import com.example.mycar.dto.FacturaDTO;
import com.example.mycar.dto.RespuestaPagoDTO;
import com.example.mycar.dto.SolicitudPagoDTO;
import com.example.mycar.entities.*;
import com.example.mycar.enums.EstadoFactura;
import com.example.mycar.enums.TipoPago;
import com.example.mycar.repositories.AlquilerRepository;
import com.example.mycar.repositories.DetalleFacturaRepository;
import com.example.mycar.repositories.FacturaRepository;
import com.example.mycar.repositories.FormaDePagoRepository;
import com.example.mycar.services.FacturaService;
import com.example.mycar.services.PagoService;
import com.example.mycar.services.mapper.FacturaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PagoServiceImpl implements PagoService {

    private final FacturaRepository facturaRepository;
    private final AlquilerRepository alquilerRepository;
    private final FormaDePagoRepository formaDePagoRepository;
    private final DetalleFacturaRepository detalleFacturaRepository;
    private final FacturaService facturaService;
    private final FacturaMapper facturaMapper;

    public PagoServiceImpl(
            FacturaRepository facturaRepository,
            AlquilerRepository alquilerRepository,
            FormaDePagoRepository formaDePagoRepository,
            DetalleFacturaRepository detalleFacturaRepository,
            FacturaService facturaService,
            FacturaMapper facturaMapper
    ) {
        this.facturaRepository = facturaRepository;
        this.alquilerRepository = alquilerRepository;
        this.formaDePagoRepository = formaDePagoRepository;
        this.detalleFacturaRepository = detalleFacturaRepository;
        this.facturaService = facturaService;
        this.facturaMapper = facturaMapper;
    }

    @Override
    @Transactional
    public RespuestaPagoDTO procesarPago(SolicitudPagoDTO solicitud) throws Exception {
        // Validar solicitud
        if (solicitud.getAlquilerIds() == null || solicitud.getAlquilerIds().isEmpty()) {
            throw new Exception("Debe proporcionar al menos un alquiler para pagar");
        }

        // Obtener alquileres
        List<Alquiler> alquileres = alquilerRepository.findByIdInAndActivoTrue(solicitud.getAlquilerIds());

        if (alquileres.isEmpty()) {
            throw new Exception("No se encontraron alquileres válidos");
        }

        // Validar que no tengan factura
        for (Alquiler alquiler : alquileres) {
            if (alquiler.getDetalleFactura() != null) {
                throw new Exception("El alquiler con ID " + alquiler.getId() + " ya tiene factura asociada");
            }
        }

        // Obtener forma de pago
        FormaDePago formaDePago = formaDePagoRepository.findByTipoPagoAndActivoTrue(solicitud.getTipoPago())
                .orElseGet(() -> {
                    // Si no existe, crear una nueva
                    FormaDePago nuevaFormaDePago = FormaDePago.builder()
                            .tipoPago(solicitud.getTipoPago())
                            .observacion("Forma de pago creada automáticamente")
                            .build();
                    nuevaFormaDePago.setActivo(true);
                    return formaDePagoRepository.save(nuevaFormaDePago);
                });

        // Calcular costos y actualizar alquileres
        BigDecimal totalAPagar = BigDecimal.ZERO;
        List<DetalleFactura> detalles = new ArrayList<>();

        for (Alquiler alquiler : alquileres) {
            // Calcular días
            long diffInMillies = Math.abs(alquiler.getFechaHasta().getTime() - alquiler.getFechaDesde().getTime());
            int dias = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if (dias == 0) dias = 1;

            // Obtener costo del vehículo
            Vehiculo vehiculo = alquiler.getVehiculo();
            if (vehiculo == null || vehiculo.getCostoVehiculo() == null) {
                throw new Exception("El alquiler " + alquiler.getId() + " no tiene vehículo con costo definido");
            }

            // Calcular subtotal
            BigDecimal costoPorDia = BigDecimal.valueOf(vehiculo.getCostoVehiculo().getCosto());
            BigDecimal subtotal = costoPorDia.multiply(BigDecimal.valueOf(dias))
                    .setScale(2, RoundingMode.HALF_UP);

            totalAPagar = totalAPagar.add(subtotal);

            // Actualizar alquiler con el costo calculado (aún no se persiste)
            alquiler.setCostoCalculado(subtotal.doubleValue());
            alquiler.setCantidadDias(dias);

            // Crear detalle de factura
            DetalleFactura detalle = new DetalleFactura();
            detalle.setCantidad(dias);
            detalle.setSubtotal(subtotal.doubleValue());
            detalle.setAlquiler(alquiler);
            detalle.setActivo(true);

            detalles.add(detalle);
        }

        // Generar número de factura
        Long numeroFactura = facturaService.generarNumeroFactura();

        // Crear factura
        Factura factura = Factura.builder()
                .numeroFactura(numeroFactura)
                .fechaFactura(LocalDate.now())
                .totalPagado(totalAPagar)
                .estado(EstadoFactura.Sin_definir) // Pendiente de aprobación
                .formaDePago(formaDePago)
                .observacionPago(solicitud.getObservacion())
                .detalles(new ArrayList<>())
                .build();
        factura.setActivo(true);

        // Guardar factura
        factura = facturaRepository.save(factura);

        // Asociar detalles a la factura y guardar
        for (DetalleFactura detalle : detalles) {
            detalle.setFactura(factura);
            detalleFacturaRepository.save(detalle);
            factura.getDetalles().add(detalle);

            // Guardar alquiler con el costo calculado
            Alquiler alquiler = detalle.getAlquiler();
            alquilerRepository.save(alquiler);
        }

        // Preparar respuesta
        String mensaje;
        String urlMercadoPago = null;

        if (solicitud.getTipoPago() == TipoPago.Billetera_virtual) {
            mensaje = "Pago pendiente de aprobación. Redirigir a Mercado Pago para completar el pago.";
            // Aquí se integraría con Mercado Pago para generar el link de pago
            // Por ahora retornamos un mensaje
            urlMercadoPago = "https://www.mercadopago.com/checkout/v1/redirect?preference-id=EXAMPLE";
        } else {
            mensaje = "Pago registrado. Pendiente de aprobación por un administrador.";
        }

        return RespuestaPagoDTO.builder()
                .facturaId(factura.getId())
                .numeroFactura(String.format("%08d", factura.getNumeroFactura()))
                .totalPagado(totalAPagar)
                .estado(factura.getEstado())
                .tipoPago(solicitud.getTipoPago())
                .mensaje(mensaje)
                .urlPagoMercadoPago(urlMercadoPago)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FacturaDTO> obtenerFacturasPendientes() throws Exception {
        List<Factura> facturas = facturaRepository.findByEstadoAndActivoTrue(EstadoFactura.Sin_definir);
        return facturaMapper.toDtoList(facturas);
    }

    @Override
    @Transactional
    public FacturaDTO aprobarFactura(Long facturaId) throws Exception {
        Factura factura = facturaRepository.findByIdAndActivoTrue(facturaId)
                .orElseThrow(() -> new Exception("No se encontró la factura con ID " + facturaId));

        if (factura.getEstado() == EstadoFactura.Pagada) {
            throw new Exception("La factura ya está aprobada");
        }

        if (factura.getEstado() == EstadoFactura.Anulada) {
            throw new Exception("No se puede aprobar una factura anulada");
        }

        factura.setEstado(EstadoFactura.Pagada);
        factura = facturaRepository.save(factura);

        return facturaMapper.toDto(factura);
    }

    @Override
    @Transactional
    public FacturaDTO anularFactura(Long facturaId, String motivo) throws Exception {
        Factura factura = facturaRepository.findByIdAndActivoTrue(facturaId)
                .orElseThrow(() -> new Exception("No se encontró la factura con ID " + facturaId));

        if (factura.getEstado() == EstadoFactura.Anulada) {
            throw new Exception("La factura ya está anulada");
        }

        factura.setEstado(EstadoFactura.Anulada);
        factura.setObservacionAnulacion(motivo);
        factura = facturaRepository.save(factura);

        // Desasociar los alquileres de la factura (para que puedan ser facturados nuevamente si es necesario)
        for (DetalleFactura detalle : factura.getDetalles()) {
            Alquiler alquiler = detalle.getAlquiler();
            if (alquiler != null) {
                alquiler.setCostoCalculado(null);
                alquiler.setCantidadDias(null);
                alquilerRepository.save(alquiler);
            }
        }

        return facturaMapper.toDto(factura);
    }
}

