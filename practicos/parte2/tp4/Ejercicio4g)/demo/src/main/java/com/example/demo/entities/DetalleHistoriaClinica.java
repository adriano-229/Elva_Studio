package com.example.demo.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_hc")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DetalleHistoriaClinica extends Base {
	
	private Date fechaHistoria;
	private String detalleHistoria;
	
	@ManyToOne
	@JoinColumn(name = "fk_historia_clinica")
	private HistoriaClinica historiaClinica;
	
	@ManyToOne
	@JoinColumn(name = "fk_medico")
	private Medico medico;
}
