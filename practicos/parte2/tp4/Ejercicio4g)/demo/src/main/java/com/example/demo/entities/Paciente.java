package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "paciente")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Paciente extends Base {
	
	private String nombre;
	private String apellido;
	private String documento;
	
	@ManyToOne
	@JoinColumn(name = "fk_foto_paciente")
	private FotoPaciente fotoPaciente;
	
}
