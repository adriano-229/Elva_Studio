package com.projects.gym.gym_app.service.dto;

import java.util.List;

import com.projects.gym.gym_app.service.dto.CuotaAdmin;
import com.projects.gym.gym_app.domain.enums.TipoPago;
import com.projects.gym.gym_app.domain.CuotaMensual;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CuotaAdmin {
	
	private CuotaMensual cuota;
	private TipoPago tipoPago; 

}
