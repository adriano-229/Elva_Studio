package com.example.biblioteca.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="libro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Libro extends Base{
	
	@Column(name="fecha")
	private int fecha;
	
	@Column(name="genero")
	private String genero;
	
	@Column(name="paginas")
	private int paginas;
	
	@Column(name="titulo")
	private String titulo;
	
	@ManyToMany(cascade = CascadeType.REFRESH) 
	//al momento de persistir un libro se mantengan actualizados los autores
	@JoinTable(
			name="libro_autor",
			joinColumns = @JoinColumn(name="libro_id"),
			inverseJoinColumns = @JoinColumn(name="autor_id"))
	private List<Autor> autores = new ArrayList<Autor>();
	
	@OneToOne
	@JoinColumn(name="fk_archivo_pdf")
	private ArchivoPdf pdf;
	
	
	

}
