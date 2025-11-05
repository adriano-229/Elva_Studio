package com.example.mycar.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empresa")
public class Empresa extends Base{
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "telefono", nullable = false)
	private String telefono;
	
	@Column(name = "correo_electronico", nullable = false)
	private String correoElectronico;
	
	@OneToOne
	private ConfiguracionCorreoAutomatico configuracionCorreoAutomatico;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_id")
	private Direccion direccion;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contacto_id")
	private Contacto contacto;
}
