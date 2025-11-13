package com.projects.mycar.mycar_server.services.impl;


import com.projects.mycar.mycar_server.dto.*;
import com.projects.mycar.mycar_server.entities.*;
import com.projects.mycar.mycar_server.repositories.AlquilerRepository;
import com.projects.mycar.mycar_server.repositories.ClienteRepository;
import com.projects.mycar.mycar_server.repositories.CodigoDescuentoRepository;
import com.projects.mycar.mycar_server.services.CostoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CostoServiceImpl implements CostoService {

    private final AlquilerRepository alquilerRepository;
    private final ClienteRepository clienteRepository;
    private final CodigoDescuentoRepository codigoDescuentoRepository;
    @Autowired
    private VehiculoServiceImpl vehiculoService;
    @Autowired
    private CodigoDescuentoServiceImpl codigoDescuentoService;

    public CostoServiceImpl(
            AlquilerRepository alquilerRepository,
            ClienteRepository clienteRepository,
            CodigoDescuentoRepository codigoDescuentoRepository) {
        this.alquilerRepository = alquilerRepository;
        this.clienteRepository = clienteRepository;
        this.codigoDescuentoRepository = codigoDescuentoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PagareDTO calcularCostosYGenerarPagare(List<Long> alquilerIds, Long clienteId) throws Exception {
        if (alquilerIds == null || alquilerIds.isEmpty()) {
            throw new Exception("Debe proporcionar al menos un alquiler");
        }
        List<Alquiler> alquileres = alquilerRepository.findByIdInAndActivoTrue(alquilerIds);
        if (alquileres.isEmpty()) {
            throw new Exception("No se encontraron alquileres validos");
        }
        for (Alquiler alquiler : alquileres) {
            if (alquiler.getDetalleFactura() != null) {
                throw new Exception("El alquiler con ID " + alquiler.getId() + " ya tiene factura asociada");
            }
        }
        List<AlquilerConCostoDTO> alquileresConCosto = new ArrayList<>();
        double totalAPagar = 0.0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Alquiler alquiler : alquileres) {
            long diasLong = ChronoUnit.DAYS.between(alquiler.getFechaDesde(), alquiler.getFechaHasta());
            if (diasLong == 0) diasLong = 1;
            int dias = (int) diasLong;
            Vehiculo vehiculo = alquiler.getVehiculo();
            if (vehiculo == null) {
                throw new Exception("El alquiler con ID " + alquiler.getId() + " no tiene vehiculo asociado");
            }
            CaracteristicaVehiculo caracteristica = vehiculo.getCaracteristicaVehiculo();
            if (caracteristica == null) {
                throw new Exception("El vehiculo " + vehiculo.getPatente() + " no tiene caracteristicas asociadas");
            }
            CostoVehiculo costoVehiculo = caracteristica.getCostoVehiculo();
            if (costoVehiculo == null) {
                throw new Exception("El vehiculo " + vehiculo.getPatente() + " no tiene costo asociado");
            }
            double costoPorDia = costoVehiculo.getCosto();
            if (costoPorDia <= 0) {
                throw new Exception("Costo del vehiculo invalido");
            }
            double subtotal = Math.round(costoPorDia * dias * 100.0) / 100.0;
            totalAPagar = Math.round((totalAPagar + subtotal) * 100.0) / 100.0;
            AlquilerConCostoDTO alquilerConCosto = AlquilerConCostoDTO.builder()
                    .alquilerId(alquiler.getId())
                    .vehiculoPatente(vehiculo.getPatente())
                    .fechaDesde(alquiler.getFechaDesde().toString())
                    .fechaHasta(alquiler.getFechaHasta().toString())
                    .cantidadDias(dias)
                    .costoPorDia(costoPorDia)
                    .subtotal(subtotal)
                    .build();
            alquileresConCosto.add(alquilerConCosto);
        }
        String clienteNombre = "Cliente sin especificar";
        double descuento = 0.0;
        Double porcentajeDescuento = 0.0;
        String codigoDescuentoStr = null;

        if (clienteId != null) {
            Cliente cliente = clienteRepository.findByIdAndActivoTrue(clienteId).orElse(null);
            if (cliente != null) {
                clienteNombre = cliente.getNombre() + " " + cliente.getApellido();

                // Buscar código de descuento válido para el cliente
                Optional<CodigoDescuento> codigoDescuentoOpt =
                        codigoDescuentoRepository.findByClienteIdAndUtilizadoFalseAndActivoTrue(clienteId);

                if (codigoDescuentoOpt.isPresent()) {
                    CodigoDescuento codigoDescuento = codigoDescuentoOpt.get();

                    // Verificar si no está expirado
                    if (codigoDescuento.getFechaExpiracion() == null ||
                            !LocalDate.now().isAfter(codigoDescuento.getFechaExpiracion())) {

                        porcentajeDescuento = codigoDescuento.getPorcentajeDescuento();
                        descuento = Math.round(totalAPagar * porcentajeDescuento / 100.0 * 100.0) / 100.0;
                        codigoDescuentoStr = codigoDescuento.getCodigo();

                        log.info("Descuento aplicado: {}% (${}) con código {} para cliente {}",
                                porcentajeDescuento, descuento, codigoDescuentoStr, clienteId);
                    } else {
                        log.info("Cliente {} tiene código expirado", clienteId);
                    }
                }
            }
        }

        Double subtotal = totalAPagar;
        totalAPagar = Math.round((totalAPagar - descuento) * 100.0) / 100.0;

        return PagareDTO.builder()
                .alquileres(alquileresConCosto)
                .subtotal(subtotal)
                .descuento(descuento)
                .porcentajeDescuento(porcentajeDescuento)
                .codigoDescuento(codigoDescuentoStr)
                .totalAPagar(totalAPagar)
                .fechaEmision(LocalDateTime.now())
                .clienteNombre(clienteNombre)
                .clienteId(clienteId)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public AlquilerFormDTO calcularCostoAlquiler(AlquilerFormDTO alquiler) throws Exception {
        long diasLong = ChronoUnit.DAYS.between(alquiler.getFechaDesde(), alquiler.getFechaHasta());
        if (diasLong == 0) diasLong = 1;
        int dias = (int) diasLong;
        alquiler.setCantidadDias(dias);

        if (alquiler.getIdVehiculo() == null) {
            throw new Exception("No se puede calcular el costo. Vehiculo no asociado");
        }

        VehiculoDTO vehiculo = vehiculoService.findById(alquiler.getIdVehiculo());

        if (vehiculo == null) {
            throw new Exception("EL vehiculo no esta registrado");
        }

        CaracteristicaVehiculoDTO caracteristica = vehiculo.getCaracteristicaVehiculo();
        if (caracteristica == null || caracteristica.getCostoVehiculo() == null) {
            throw new Exception("No se puede calcular el costo. Vehiculo sin costo definido.");
        }

        alquiler.setCostoPorDia(caracteristica.getCostoVehiculo().getCosto());

        double subtotal = caracteristica.getCostoVehiculo().getCosto() * dias;
        alquiler.setSubtotal(subtotal);

        double descuento = 0;
        System.out.println("CODIGO DESCUENTO: " + alquiler.getCodigoDescuento());
        if (alquiler.getCodigoDescuento() != null && !alquiler.getCodigoDescuento().isBlank()) {
            CodigoDescuentoDTO codigoDescuento = codigoDescuentoService.findByCodigo(alquiler.getCodigoDescuento());

            if (codigoDescuento == null) {
                throw new Exception("El codigo de descuento no es válido");
            }

            alquiler.setPorcentajeDescuento(codigoDescuento.getPorcentajeDescuento());

            descuento = (subtotal * codigoDescuento.getPorcentajeDescuento()) / 100;
            alquiler.setDescuento(descuento);

        }

        alquiler.setTotalConDescuento(subtotal - descuento);


        return alquiler;
    }

    /*@Override
    @Transactional(readOnly = true)
    public Double calcularCostoAlquiler(Long alquilerId) throws Exception {
        Alquiler alquiler = alquilerRepository.findByIdAndActivoTrue(alquilerId)
                .orElseThrow(() -> new Exception("No se encontró el alquiler con ID " + alquilerId));
        long diasLong = ChronoUnit.DAYS.between(alquiler.getFechaDesde(), alquiler.getFechaHasta());
        if (diasLong == 0) diasLong = 1;
        int dias = (int) diasLong;
        Vehiculo vehiculo = alquiler.getVehiculo();
        if (vehiculo == null) {
            throw new Exception("No se puede calcular el costo. Vehiculo no asociado");
        }
        CaracteristicaVehiculo caracteristica = vehiculo.getCaracteristicaVehiculo();
        if (caracteristica == null || caracteristica.getCostoVehiculo() == null) {
            throw new Exception("No se puede calcular el costo. Vehiculo sin costo definido.");
        }
        return caracteristica.getCostoVehiculo().getCosto() * dias;
    }*/
}
