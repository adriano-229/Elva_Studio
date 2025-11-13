package com.projects.mycar.mycar_server.controller;

import com.projects.mycar.mycar_server.dto.FacturaDTO;
import com.projects.mycar.mycar_server.dto.RespuestaPagoDTO;
import com.projects.mycar.mycar_server.dto.SolicitudPagoDTO;
import com.projects.mycar.mycar_server.services.PagoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {
    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping("/procesar")
    public ResponseEntity<?> procesarPago(@Valid @RequestBody SolicitudPagoDTO solicitud) {
        try {
            RespuestaPagoDTO respuesta = pagoService.procesarPago(solicitud);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/pendientes")
    public ResponseEntity<?> obtenerPagosPendientes() {
        try {
            List<FacturaDTO> facturas = pagoService.obtenerFacturasPendientes();
            return ResponseEntity.ok(facturas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/aprobar/{facturaId}")
    public ResponseEntity<?> aprobarPago(@PathVariable Long facturaId) {
        try {
            FacturaDTO factura = pagoService.aprobarFactura(facturaId);
            return ResponseEntity.ok(factura);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/anular/{facturaId}")
    public ResponseEntity<?> anularPago(@PathVariable Long facturaId, @RequestParam String motivo) {
        try {
            FacturaDTO factura = pagoService.anularFactura(facturaId, motivo);
            return ResponseEntity.ok(factura);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
