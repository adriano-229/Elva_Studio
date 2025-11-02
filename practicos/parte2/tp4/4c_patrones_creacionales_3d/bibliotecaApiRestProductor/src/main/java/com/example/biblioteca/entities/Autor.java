package com.example.biblioteca.entities;

import java.util.List;

import com.example.biblioteca.entities.enums.EstadoLibro;
import com.example.biblioteca.entities.enums.TipoLibro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="autor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Autor extends Base implements AutorPrototype{
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="apellido")
	private String apellido;
	
	@Column(name="biografia", length = 1500)
	private String biografia;

	@Override
	public Autor cloneAutor() {
		
		return Autor.builder()
                .nombre(this.nombre)
                .apellido(this.apellido)
                .biografia(this.biografia)
                .activo(isActivo())
                .build();
	}

}
