package com.example.mycar.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "configuracion_correo_automatico")
public class ConfiguracionCorreoAutomatico extends Base {
	
	@Column(name = "correo", nullable = false)
	private String correo;
	
	@Column(name = "puerto", nullable = false)
	private String puerto;
	
	@Column(name = "clave", nullable = false)
	private String clave;
	
	@Column(name = "smtp", nullable = false)
	private String smtp;
	
	@Column(name = "tls", nullable = false)
	private boolean tls;
	
}
