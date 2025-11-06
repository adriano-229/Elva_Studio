package com.example.mycar.services.impl;

import com.example.mycar.dto.AlquilerConCostoDTO;
import com.example.mycar.dto.PagareDTO;
import com.example.mycar.entities.Alquiler;
import com.example.mycar.entities.Cliente;
import com.example.mycar.entities.CostoVehiculo;
import com.example.mycar.entities.Vehiculo;
import com.example.mycar.repositories.AlquilerRepository;
import com.example.mycar.repositories.ClienteRepository;
import com.example.mycar.services.CostoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

        // Obtener alquileres
        List<Alquiler> alquileres = alquilerRepository.findByIdInAndActivoTrue(alquilerIds);

        if (alquileres.isEmpty()) {
            throw new Exception("No se encontraron alquileres válidos");
        }

        // Validar que los alquileres no tengan factura
        for (Alquiler alquiler : alquileres) {
            if (alquiler.getDetalleFactura() != null) {
                throw new Exception("El alquiler con ID " + alquiler.getId() + " ya tiene factura asociada");
            }
        }

        List<AlquilerConCostoDTO> alquileresConCosto = new ArrayList<>();
        BigDecimal totalAPagar = BigDecimal.ZERO;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Calcular costo de cada alquiler
        for (Alquiler alquiler : alquileres) {
            // Calcular días
            long diffInMillies = Math.abs(alquiler.getFechaHasta().getTime() - alquiler.getFechaDesde().getTime());
            int dias = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if (dias == 0) dias = 1; // Mínimo 1 día

            // Obtener costo del vehículo
            Vehiculo vehiculo = alquiler.getVehiculo();
            if (vehiculo == null) {
                throw new Exception("El alquiler con ID " + alquiler.getId() + " no tiene vehículo asociado");
            }

            CostoVehiculo costoVehiculo = vehiculo.getCostoVehiculo();
            if (costoVehiculo == null) {
                throw new Exception("El vehículo " + vehiculo.getPatente() + " no tiene costo asociado");
            }

            // Calcular subtotal
            BigDecimal costoPorDia = BigDecimal.valueOf(costoVehiculo.getCosto());
            BigDecimal subtotal = costoPorDia.multiply(BigDecimal.valueOf(dias))
                    .setScale(2, RoundingMode.HALF_UP);

            totalAPagar = totalAPagar.add(subtotal);

            // CrearDTO
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

        // Obtener información del cliente si se proporcionó
        String clienteNombre = "Cliente sin especificar";
        if (clienteId != null) {
            Cliente cliente = clienteRepository.findByIdAndActivoTrue(clienteId)
                    .orElse(null);
            if (cliente != null) {
                clienteNombre = cliente.getNombre() + " " + cliente.getApellido();
            }
        }

        // Generar pagaré
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
                .orElseThrow(() -> new Exception("No se encontró el alquiler con ID " + alquilerId));

        // Calcular días
        long diffInMillies = Math.abs(alquiler.getFechaHasta().getTime() - alquiler.getFechaDesde().getTime());
        int dias = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if (dias == 0) dias = 1;

        // Obtener costo del vehículo
        Vehiculo vehiculo = alquiler.getVehiculo();
        if (vehiculo == null || vehiculo.getCostoVehiculo() == null) {
            throw new Exception("No se puede calcular el costo. Vehículo sin costo definido.");
        }

        return vehiculo.getCostoVehiculo().getCosto() * dias;
    }
}

