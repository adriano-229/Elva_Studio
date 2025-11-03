package com.projects.gym.gym_app.controller.rest;

import com.projects.gym.gym_app.controller.rest.dto.FacturaAnulacionRequest;
import com.projects.gym.gym_app.service.FacturaService;
import com.projects.gym.gym_app.service.dto.CuotaPendienteDTO;
import com.projects.gym.gym_app.service.dto.EmisionFacturaCommand;
import com.projects.gym.gym_app.service.dto.FacturaDTO;
import com.projects.gym.gym_app.service.dto.PagoCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/facturas")
@RequiredArgsConstructor
public class FacturasRestController {

    private final FacturaService facturaService;

    @GetMapping
    public Page<FacturaDTO> listar(@RequestParam(required = false) Long socioId,
                                   @RequestParam(required = false) String estado,
                                   @RequestParam(required = false) String desde,
                                   @RequestParam(required = false) String hasta,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "fechaFactura"));
        String socioIdStr = socioId != null ? socioId.toString() : null;
        return facturaService.listar(socioIdStr, estado, desde, hasta, pageable);
    }

    @GetMapping("/{id}")
    public FacturaDTO buscar(@PathVariable String id) {
        return facturaService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<FacturaDTO> crear(@Valid @RequestBody EmisionFacturaCommand command) {
        FacturaDTO factura = facturaService.crearFactura(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(factura);
    }

    @PostMapping("/{id}/confirmar")
    public ResponseEntity<FacturaDTO> confirmarPago(@PathVariable String id,
                                                    @Valid @RequestBody PagoCommand pago) {
        FacturaDTO factura = facturaService.confirmarPagoManual(id, pago);
        return ResponseEntity.ok(factura);
    }

    @PostMapping("/{id}/anular")
    public ResponseEntity<FacturaDTO> anular(@PathVariable String id,
                                             @RequestBody(required = false) FacturaAnulacionRequest request) {
        String observacion = request != null ? request.observacion() : null;
        FacturaDTO factura = facturaService.anularFactura(id, observacion);
        return ResponseEntity.ok(factura);
    }

    @GetMapping("/cuotas-pendientes")
    public List<CuotaPendienteDTO> cuotasPendientes(@RequestParam Long socioId) {
        return facturaService.cuotasPendientes(socioId);
    }
}
