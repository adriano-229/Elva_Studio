package com.projects.gym.gym_app.service.impl;

import com.projects.gym.gym_app.domain.CuotaMensual;
import com.projects.gym.gym_app.domain.DetalleFactura;
import com.projects.gym.gym_app.domain.Factura;
import com.projects.gym.gym_app.domain.FormaDePago;
import com.projects.gym.gym_app.domain.Socio;
import com.projects.gym.gym_app.domain.enums.EstadoCuota;
import com.projects.gym.gym_app.domain.enums.EstadoFactura;
import com.projects.gym.gym_app.domain.enums.TipoPago;
import com.projects.gym.gym_app.repository.CuotaMensualRepository;
import com.projects.gym.gym_app.repository.FacturaRepository;
import com.projects.gym.gym_app.repository.FormaDePagoRepository;
import com.projects.gym.gym_app.repository.SocioRepository;
import com.projects.gym.gym_app.service.FacturaService;
import com.projects.gym.gym_app.service.dto.CuotaPendienteDTO;
import com.projects.gym.gym_app.service.dto.EmisionFacturaCommand;
import com.projects.gym.gym_app.service.dto.FacturaDTO;
import com.projects.gym.gym_app.service.dto.PagoCommand;
import com.projects.gym.gym_app.service.mapper.FacturaMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FacturaServiceImpl implements FacturaService {

    private final FacturaRepository facturaRepository;
    private final SocioRepository socioRepository;
    private final CuotaMensualRepository cuotaMensualRepository;
    private final FormaDePagoRepository formaDePagoRepository;

    @Override
    public FacturaDTO crearFactura(EmisionFacturaCommand cmd) {
        Objects.requireNonNull(cmd, "El comando de emisión es obligatorio");
        if (cmd.getSocioId() == null) {
            throw new IllegalArgumentException("Debe seleccionar un socio");
        }
        if (cmd.getCuotasIds() == null || cmd.getCuotasIds().isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar al menos una cuota");
        }

        Socio socio = socioRepository.findById(cmd.getSocioId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el socio indicado"));

        List<CuotaMensual> cuotas = cuotaMensualRepository.findAllById(cmd.getCuotasIds());
        validarCuotasSeleccionadas(cmd.getCuotasIds(), socio, cuotas);

        Factura factura = Factura.builder()
                .numeroFactura(siguienteNumeroFactura())
                .fechaFactura(LocalDate.now())
                .socio(socio)
                .estado(EstadoFactura.SIN_DEFINIR)
                .observacionPago(cmd.getObservacionPago())
                .build();

        List<DetalleFactura> detalles = cuotas.stream()
                .map(cuota -> DetalleFactura.builder()
                        .factura(factura)
                        .cuotaMensual(cuota)
                        .importe(obtenerImporteCuota(cuota))
                        .eliminado(false)
                        .build())
                .toList();

        factura.setDetalles(detalles);
        factura.setTotalPagado(calcularTotal(detalles));

        if (cmd.isMarcarPagada()) {
            if (cmd.getFormaDePagoId() == null || cmd.getFormaDePagoId().isBlank()) {
                throw new IllegalArgumentException("Debe indicar la forma de pago");
            }
            FormaDePago forma = formaDePagoRepository.findById(cmd.getFormaDePagoId())
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró la forma de pago"));
            
            //validarFormaDePagoManual(forma);
            factura.setFormaDePago(forma);
            factura.setEstado(EstadoFactura.PAGADA);
            actualizarEstadoCuotas(cuotas, EstadoCuota.PAGADA);
        }

        Factura guardada = facturaRepository.save(factura);
        return FacturaMapper.toDto(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public FacturaDTO buscarPorId(String id) {
        Factura factura = facturaRepository.findById(id)
                .filter(f -> !f.isEliminado())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la factura solicitada"));
        return FacturaMapper.toDto(factura);
    }
    
    @Transactional(readOnly = true)
    public Factura buscarPorIdFactura(String id) {
        return facturaRepository.findById(id)
                .filter(f -> !f.isEliminado())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la factura solicitada"));
        
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FacturaDTO> listar(String socioId, String estado, String desde, String hasta, Pageable pageable) {
        Specification<Factura> spec = Specification.where((root, query, cb) -> cb.isFalse(root.get("eliminado")));

        if (socioId != null && !socioId.isBlank()) {
            try {
                long value = Long.parseLong(socioId.trim());
                spec = spec.and((root, query, cb) -> cb.equal(root.get("socio").get("id"), value));
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("El identificador del socio es inválido", ex);
            }
        }
        if (estado != null && !estado.isBlank()) {
            EstadoFactura estadoFactura = EstadoFactura.valueOf(estado);
            spec = spec.and((root, query, cb) -> cb.equal(root.get("estado"), estadoFactura));
        }
        if (desde != null && !desde.isBlank()) {
            LocalDate fecha = LocalDate.parse(desde);
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("fechaFactura"), fecha));
        }
        if (hasta != null && !hasta.isBlank()) {
            LocalDate fecha = LocalDate.parse(hasta);
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("fechaFactura"), fecha));
        }

        return facturaRepository.findAll(spec, pageable)
                .map(FacturaMapper::toDto);
    }

    @Override
    public FacturaDTO confirmarPagoManual(String facturaId, PagoCommand cmd) {
        if (cmd.getFormaDePagoId() == null || cmd.getFormaDePagoId().isBlank()) {
            throw new IllegalArgumentException("Debe seleccionar una forma de pago");
        }

        Factura factura = facturaRepository.findById(facturaId)
                .filter(f -> !f.isEliminado())
                .orElseThrow(() -> new EntityNotFoundException("La factura no existe"));

        if (factura.getEstado() == EstadoFactura.ANULADA) {
            throw new IllegalStateException("No es posible confirmar una factura anulada");
        }
        if (factura.getEstado() == EstadoFactura.PAGADA) {
            return FacturaMapper.toDto(factura);
        }

        FormaDePago forma = formaDePagoRepository.findById(cmd.getFormaDePagoId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la forma de pago"));
        validarFormaDePagoManual(forma);

        factura.setFormaDePago(forma);
        factura.setEstado(EstadoFactura.PAGADA);
        factura.setTotalPagado(calcularTotal(factura.getDetalles()));
        factura.setObservacionPago(construirObservacion(cmd));

        List<CuotaMensual> cuotas = factura.getDetalles().stream()
                .map(DetalleFactura::getCuotaMensual)
                .filter(Objects::nonNull)
                .toList();
        actualizarEstadoCuotas(cuotas, EstadoCuota.PAGADA);
        
        facturaRepository.save(factura);
        
        return FacturaMapper.toDto(factura);
    }

    @Override
    public FacturaDTO anularFactura(String facturaId, String observacion) {
        Factura factura = facturaRepository.findById(facturaId)
                .filter(f -> !f.isEliminado())
                .orElseThrow(() -> new EntityNotFoundException("La factura no existe"));

        factura.setEstado(EstadoFactura.ANULADA);
        factura.setObservacionAnulacion(observacion);

        List<CuotaMensual> cuotas = factura.getDetalles().stream()
                .map(DetalleFactura::getCuotaMensual)
                .filter(Objects::nonNull)
                .toList();
        actualizarEstadoCuotas(cuotas, EstadoCuota.ADEUDADA);

        return FacturaMapper.toDto(factura);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuotaPendienteDTO> cuotasPendientes(Long socioId) {
        if (socioId == null) {
            throw new IllegalArgumentException("Debe indicar el socio");
        }
        Socio socio = socioRepository.findById(socioId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el socio"));

        return cuotaMensualRepository.listarCuotaMensualPorEstado(socio, EstadoCuota.ADEUDADA).stream()
                .sorted(Comparator.comparing(CuotaMensual::getAnio).thenComparing(c -> c.getMes().ordinal()))
                .map(cuota -> new CuotaPendienteDTO(
                        cuota.getId(),
                        cuota.getMes(),
                        cuota.getAnio(),
                        obtenerImporteCuota(cuota)))
                .toList();
    }

    private void validarCuotasSeleccionadas(List<String> idsSolicitados, Socio socio, List<CuotaMensual> cuotas) {
        Set<String> encontrados = cuotas.stream().map(CuotaMensual::getId).collect(Collectors.toSet());
        List<String> faltantes = idsSolicitados.stream()
                .filter(id -> !encontrados.contains(id))
                .toList();
        if (!faltantes.isEmpty()) {
            throw new IllegalArgumentException("Hay cuotas que no se encontraron: " + faltantes);
        }
        boolean algunaNoPertenece = cuotas.stream().anyMatch(c -> !Objects.equals(c.getSocio().getId(), socio.getId()));
        if (algunaNoPertenece) {
            throw new IllegalArgumentException("Todas las cuotas deben pertenecer al socio seleccionado");
        }
    }

    private long siguienteNumeroFactura() {
        return facturaRepository.findFirstByEliminadoFalseOrderByNumeroFacturaDesc()
                .map(Factura::getNumeroFactura)
                .map(n -> n + 1)
                .orElse(1L);
    }

    private BigDecimal obtenerImporteCuota(CuotaMensual cuota) {
        return Optional.ofNullable(cuota.getValorCuota())
                .map(valor -> Optional.ofNullable(valor.getValorCuota()).orElse(BigDecimal.ZERO))
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal calcularTotal(List<DetalleFactura> detalles) {
        return detalles.stream()
                .map(detalle -> Optional.ofNullable(detalle.getImporte()).orElse(BigDecimal.ZERO))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void actualizarEstadoCuotas(List<CuotaMensual> cuotas, EstadoCuota estado) {
        cuotas.forEach(cuota -> {
            cuota.setEstado(estado);
            cuotaMensualRepository.save(cuota);
        });
    }

    private void validarFormaDePagoManual(FormaDePago forma) {
        if (forma.isEliminado()) {
            throw new IllegalArgumentException("La forma de pago seleccionada no está disponible");
        }
        if (forma.getTipoPago() == TipoPago.BILLETERA_VIRTUAL) {
            throw new IllegalArgumentException("Los pagos online se confirman desde Mercado Pago");
        }
    }

    private String construirObservacion(PagoCommand cmd) {
        String observacion = Optional.ofNullable(cmd.getObservacion()).map(String::trim).filter(s -> !s.isBlank()).orElse(null);
        String comprobante = Optional.ofNullable(cmd.getComprobante()).map(String::trim).filter(s -> !s.isBlank()).orElse(null);
        if (observacion == null && comprobante == null) {
            return null;
        }
        if (observacion != null && comprobante != null) {
            return observacion + " | Comprobante: " + comprobante;
        }
        return observacion != null ? observacion : "Comprobante: " + comprobante;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Factura> listarPagadas(Long socioId) throws Exception {
    	
        try {
        	
            if (socioId == null) return Collections.emptyList();
            
            // Convertimos Long a String para que coincida con el repositorio
            
            List<Factura> facturas = facturaRepository.findBySocio_Id(socioId);

            if (facturas.isEmpty()) {
                System.out.println("EN EL SERVICIO ES VACIA");
            }else {
            	System.out.println("NO ES VACIA: " + facturas.size());
            }
            
            

            return facturas;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al listar facturas de socio");
        }
    }

}
