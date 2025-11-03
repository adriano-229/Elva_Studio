package com.example.mycar.entities;

import com.example.mycar.enums.TipoEmpleado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@PrimaryKeyJoinColumn(name = "persona_id")
@Getter
@Setter
@NoArgsConstructor
public class Cliente extends Persona{
	
	@Column(name = "direccion_estadia", nullable = false)
	private String direccionEstadia;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nacionalidad_id")
	private Nacionalidad nacionalidad;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alquiler_id")
	private Alquiler alquiler;
}
