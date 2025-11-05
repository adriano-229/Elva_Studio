package com.example.mycar.entities;

import com.example.mycar.enums.TipoImagen;
import com.example.mycar.enums.TipoPago;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity 
@Table(name = "imagen")
public class Imagen extends Base {
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "mime", nullable = false)
	private String mime;
	
	@Column(name = "contenido", nullable = false)
	private byte[] contenido;
	
	@Enumerated(EnumType.STRING)
	private TipoImagen tipoImagen;
	
	
}
