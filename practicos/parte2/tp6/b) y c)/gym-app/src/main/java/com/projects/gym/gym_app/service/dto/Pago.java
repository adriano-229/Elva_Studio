package com.projects.gym.gym_app.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.projects.gym.gym_app.domain.enums.TipoPago;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Pago {
	
	private Long idSocio;
    
    private BigDecimal totalAPagar;
    
    private List<String> idCuotas;
    
    private TipoPago tipoPago;
    
    private LocalDateTime fechaRecepcion;

}
