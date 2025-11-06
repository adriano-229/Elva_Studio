package com.projects.gym.gym_app.service.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EnvioPromocionCommand {
    /**
     * Alcance: "TODOS" | "CUMPLEANIOS"
     */
    @NotBlank
    private String alcance;
}
