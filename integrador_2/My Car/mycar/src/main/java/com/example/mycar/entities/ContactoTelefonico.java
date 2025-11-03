package com.example.mycar.entities;

import com.example.mycar.enums.TipoTelefono;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@PrimaryKeyJoinColumn(name = "contacto_id")
@Getter
@Setter
@NoArgsConstructor
public class ContactoTelefonico extends Contacto{
	
	@Column(name = "telefono", nullable = false)
	private String telefono;
	
	@Column(name = "tipo_telefono", nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoTelefono tipoTelefono;
}
