package com.example.mycar.controller;

import com.example.mycar.dto.AlquilerFormDTO;
import com.example.mycar.dto.PagareDTO;
import com.example.mycar.services.CostoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar el cálculo de costos de alquileres.
 */
@RestController
@RequestMapping("/api/costos")
public class CostoController {

    private final CostoService costoService;

    public CostoController(CostoService costoService) {
        this.costoService = costoService;
    }

    /**
     * Calcula los costos de múltiples alquileres y genera un "pagaré" temporal.
     * Este pagaré no se persiste, solo se muestra al cliente.
     *
     * @param alquilerIds Lista de IDs de alquileres a calcular
     * @param clienteId   ID del cliente (opcional)
     * @return PagareDTO con los costos calculados
     */
    @PostMapping("/calcular-pagare")
    public ResponseEntity<?> calcularPagare(
            @RequestParam List<Long> alquilerIds,
            @RequestParam(required = false) Long clienteId
    ) {
        try {
            PagareDTO pagare = costoService.calcularCostosYGenerarPagare(alquilerIds, clienteId);
            return ResponseEntity.ok(pagare);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Calcula el costo de un único alquiler.
     *
     * @param alquilerId ID del alquiler
     * @return Costo calculado
     */
    /*@GetMapping("/calcular/{alquilerId}")
    public ResponseEntity<?> calcularCostoAlquiler(@PathVariable Long alquilerId) {
        try {
            Double costo = costoService.calcularCostoAlquiler(alquilerId);
            return ResponseEntity.ok("{\"costo\":" + costo + "}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }*/
    @PostMapping("/calcularCosto")
    public ResponseEntity<?> calcularCostoAlquiler(@RequestBody AlquilerFormDTO alquiler) {
        try {
            AlquilerFormDTO costo = costoService.calcularCostoAlquiler(alquiler);
            return ResponseEntity.ok(costo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}

