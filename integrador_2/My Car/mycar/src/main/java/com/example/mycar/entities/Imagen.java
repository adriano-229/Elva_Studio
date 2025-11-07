package com.example.mycar.entities;

import com.example.mycar.enums.TipoImagen;
import jakarta.persistence.*;
import lombok.*;

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
