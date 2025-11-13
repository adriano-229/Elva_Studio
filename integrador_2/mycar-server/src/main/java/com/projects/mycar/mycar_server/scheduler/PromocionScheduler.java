package com.projects.mycar.mycar_server.scheduler;

import com.projects.mycar.mycar_server.services.PromocionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PromocionScheduler {

    private final PromocionService promocionService;

    public PromocionScheduler(PromocionService promocionService) {
        this.promocionService = promocionService;
    }

    /**
     * Se ejecuta el día 1 de cada mes a las 9:00 AM
     * Cron format: segundo minuto hora día mes día-de-semana
     */
    @Scheduled(cron = "0 0 9 1 * ?")
    public void enviarPromocionesDelMes() {
        log.info("========================================");
        log.info("Ejecutando tarea programada: Envío de promociones mensuales");
        log.info("========================================");

        try {
            promocionService.generarYEnviarPromocionesAutomaticas();
            log.info("Tarea de envío de promociones completada exitosamente");
        } catch (Exception e) {
            log.error("Error en la tarea programada de promociones: {}", e.getMessage(), e);
        }
    }
}

