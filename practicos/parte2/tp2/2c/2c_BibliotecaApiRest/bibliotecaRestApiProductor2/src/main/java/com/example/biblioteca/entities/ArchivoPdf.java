package com.example.biblioteca.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="archivo_pdf")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoPdf extends Base{
	
	@Column(name="nombre")
	private String nombrePdf;
	
	@Column(name="ruta_pdf")
	private String rutaPdf;
	
	

}
