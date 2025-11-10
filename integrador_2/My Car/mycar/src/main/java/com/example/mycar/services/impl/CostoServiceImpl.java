package com.example.mycar.services.impl;

import com.example.mycar.dto.AlquilerConCostoDTO;
import com.example.mycar.dto.PagareDTO;
import com.example.mycar.entities.*;
import com.example.mycar.repositories.AlquilerRepository;
import com.example.mycar.repositories.ClienteRepository;
import com.example.mycar.services.CostoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class CostoServiceImpl implements CostoService {

    private final AlquilerRepository alquilerRepository;
    private final ClienteRepository clienteRepository;

    public CostoServiceImpl(AlquilerRepository alquilerRepository, ClienteRepository clienteRepository) {
        this.alquilerRepository = alquilerRepository;
        this.clienteRepository = clienteRepository;
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
                    .fechaDesde(sdf.format(alquiler.getFechaDesde()))
                    .fechaHasta(sdf.format(alquiler.getFechaHasta()))
                    .cantidadDias(dias)
                    .costoPorDia(costoPorDia)
                    .subtotal(subtotal)
                    .build();
            alquileresConCosto.add(alquilerConCosto);
        }
        String clienteNombre = "Cliente sin especificar";
        if (clienteId != null) {
            Cliente cliente = clienteRepository.findByIdAndActivoTrue(clienteId).orElse(null);
            if (cliente != null) {
                clienteNombre = cliente.getNombre() + " " + cliente.getApellido();
            }
        }
        return PagareDTO.builder()
                .alquileres(alquileresConCosto)
                .totalAPagar(totalAPagar)
                .fechaEmision(LocalDateTime.now())
                .clienteNombre(clienteNombre)
                .clienteId(clienteId)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Double calcularCostoAlquiler(Long alquilerId) throws Exception {
        Alquiler alquiler = alquilerRepository.findByIdAndActivoTrue(alquilerId)
                .orElseThrow(() -> new Exception("No se encontro el alquiler con ID " + alquilerId));
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
    }
}
