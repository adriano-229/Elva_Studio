package com.example.biblioteca.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("DIGITAL")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class LibroDigital extends Libro{
	
	@Column(name="ruta_pdf")
	private String rutaPdf;

}
