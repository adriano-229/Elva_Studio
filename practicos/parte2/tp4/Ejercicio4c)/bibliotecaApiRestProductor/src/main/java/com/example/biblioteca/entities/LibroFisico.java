package com.example.biblioteca.entities;

import com.example.biblioteca.entities.enums.EstadoLibro;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("FISICO")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class LibroFisico extends Libro{
	
	 @Column(name = "ejemplares_disponibles")
	 private int cantEjemplares;
	 
	 

}
