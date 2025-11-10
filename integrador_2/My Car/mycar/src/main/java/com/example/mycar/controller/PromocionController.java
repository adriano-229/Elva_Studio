package com.example.mycar.controller;

import com.example.mycar.dto.ConfiguracionPromocionDTO;
import com.example.mycar.services.PromocionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/promociones")
public class PromocionController {

    private final PromocionService promocionService;

    public PromocionController(PromocionService promocionService) {
        this.promocionService = promocionService;
    }

    /**
     * Configura el porcentaje de descuento y mensaje de promoción
     */
    @PostMapping("/configurar")
    public ResponseEntity<?> configurarPromocion(@RequestBody ConfiguracionPromocionDTO configuracionDTO) {
        try {
            if (configuracionDTO.getPorcentajeDescuento() == null ||
                    configuracionDTO.getPorcentajeDescuento() <= 0 ||
                    configuracionDTO.getPorcentajeDescuento() > 100) {
                return ResponseEntity.badRequest()
                        .body("{\"error\":\"El porcentaje de descuento debe estar entre 0 y 100\"}");
            }

            if (configuracionDTO.getMensajePromocion() == null ||
                    configuracionDTO.getMensajePromocion().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("{\"error\":\"Debe proporcionar un mensaje de promoción\"}");
            }

            ConfiguracionPromocionDTO resultado =
                    promocionService.crearOActualizarConfiguracion(configuracionDTO);

            log.info("Configuración de promoción actualizada: {}% - {}",
                    resultado.getPorcentajeDescuento(),
                    resultado.getMensajePromocion());

            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            log.error("Error al configurar promoción: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Obtiene la configuración de promoción activa
     */
    @GetMapping("/configuracion-activa")
    public ResponseEntity<?> obtenerConfiguracionActiva() {
        try {
            ConfiguracionPromocionDTO config = promocionService.obtenerConfiguracionActiva();
            return ResponseEntity.ok(config);
        } catch (Exception e) {
            log.error("Error al obtener configuración: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Ejecuta el envío de promociones manualmente (para pruebas)
     */
    @PostMapping("/enviar-manual")
    public ResponseEntity<?> enviarPromocionesManual() {
        try {
            log.info("Ejecutando envío manual de promociones...");
            promocionService.generarYEnviarPromocionesAutomaticas();
            return ResponseEntity.ok("{\"mensaje\":\"Promociones enviadas exitosamente\"}");
        } catch (Exception e) {
            log.error("Error al enviar promociones: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}

