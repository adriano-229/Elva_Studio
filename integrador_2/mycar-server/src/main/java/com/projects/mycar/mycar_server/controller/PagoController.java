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
    
    /*@GetMapping
    public ResponseEntity<?> getAll() {
        try {
        	return ResponseEntity.status(HttpStatus.OK).body(pagoService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. por favor intente más tarde.\"}");
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(pagoService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. por favor intente más tarde.\"}");
        }
    }
    
    @PostMapping
    public ResponseEntity<?> save(@RequestBody D dto) {
        try {
        	System.out.println("CONTROLLER SERVICE");
            return ResponseEntity.status(HttpStatus.OK).body(pagoService.save(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. por favor intente más tarde.\"}");
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody D dto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(pagoService.update(id, dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. por favor intente más tarde.\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(pagoService.delete(id));
        } catch (Exception e) {
        	System.out.println("ERRROR AL ELIMINAR");
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. por favor intente más tarde.\"}");
        }

    }*/
}
