package com.projects.gym.gym_app.service.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.projects.gym.gym_app.domain.enums.TipoPago;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeudaForm {
	
	private Long idSocio;
	private BigDecimal totalAPagar;
	private List<String> idCuotas = new ArrayList<>();
	private TipoPago formaPago;
	private MultipartFile comprobante;
	

}
