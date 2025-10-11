package com.elva.tp1.tasks;

import com.elva.tp1.service.EmailService;
import com.elva.tp1.service.PersonaService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ScheduleTask {

    private final PersonaService personaService;
    private final EmailService emailService;
    private final String zone = "America/Argentina/Buenos_Aires";

    public ScheduleTask(PersonaService personaService, EmailService emailService) {
        this.personaService = personaService;
        this.emailService = emailService;
    }

//    @Scheduled(cron = "0 0 15 5 1/1 ?", zone = zone)
    @Scheduled(fixedRate = 5000)
    public void schedulePersonasMarketingEmailTask() {
        personaService.findAll().forEach(persona -> {
            try {
                emailService.sendPersonasMarketingEmailTask(
                        persona.getEmail(),
                        persona.getNombre(),
                        persona.getApellido(),
                        "https://ingenieria.uncuyo.edu.ar/"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
