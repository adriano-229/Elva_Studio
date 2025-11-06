package com.example.contactosApp.domain;

import com.example.contactosApp.domain.enums.TipoContacto;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_subcontacto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Contacto extends Base{
	
	@Enumerated(EnumType.STRING)
	private TipoContacto tipoContacto;
	
	private String observacion;
	
	@ManyToOne
	@JoinColumn(name="fk_empresa")
	private Empresa empresa;
	
	@ManyToOne
	@JoinColumn(name="fk_persona")
	private Persona persona;
	
}
