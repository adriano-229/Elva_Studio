package com.example.mycar.scheduler;

import java.time.LocalDate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.mycar.dto.recordatorios.RecordatorioJobResponse;
import com.example.mycar.services.RecordatorioService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecordatorioScheduler {

    private final RecordatorioService recordatorioService;

    @Scheduled(cron = "${app.recordatorios.devoluciones-cron:0 0 8 * * *}",
            zone = "${app.recordatorios.timezone:America/Argentina/Buenos_Aires}")
    public void ejecutarRecordatoriosT1() {
        LocalDate fechaObjetivo = LocalDate.now().plusDays(1);
        RecordatorioJobResponse response = recordatorioService.enviarRecordatoriosProgramados(fechaObjetivo);
        log.info("Recordatorios T-1 ejecutados para {}: enviados {} de {}", fechaObjetivo,
                response.totalEnviados(), response.totalClientes());
    }
}
