package com.example.contactosApp.domain;

import com.example.contactosApp.domain.enums.TipoTelefono;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("TELEFONICO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ContactoTelefonico extends Contacto{
	
	private String telefono;
	
	@Enumerated(EnumType.STRING)
	private TipoTelefono tipoTelefono;
	
}
