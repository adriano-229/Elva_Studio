package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "historia_clinica")
public class HistoriaClinica extends Base {
	
	@ManyToOne
	@JoinColumn(name = "fk_paciente")
	private Paciente paciente;
	
	@ManyToOne
	@JoinColumn(name = "fk_usuario")
	private Usuario usuario;
}
