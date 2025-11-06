package com.example.biblioteca.entities;

import java.util.ArrayList;
import java.util.List;

import com.example.biblioteca.entities.enums.EstadoLibro;
import com.example.biblioteca.entities.enums.TipoLibro;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="libro")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Libro extends Base{
	
	@Column(name="fecha")
	private int fecha;
	
	@Column(name="genero")
	private String genero;
	
	@Column(name="paginas")
	private int paginas;
	
	@Column(name="titulo")
	private String titulo;
	
	@Enumerated(EnumType.STRING)
	@Column(name="estado")
	private EstadoLibro estado;
	
	@Transient
	@Enumerated(EnumType.STRING)
	private TipoLibro tipo;
	
	@Transient
	private int cantEjemplares;
	
	@ManyToMany(cascade = CascadeType.REFRESH) 
	//al momento de persistir un libro se mantengan actualizados los autores
	@JoinTable(
			name="libro_autor",
			joinColumns = @JoinColumn(name="libro_id"),
			inverseJoinColumns = @JoinColumn(name="autor_id"))
	private List<Autor> autores = new ArrayList<Autor>();
	
	@Transient
	public TipoLibro getTipo() {
	    if (this instanceof LibroFisico) {
	        return TipoLibro.FISICO;
	    } else if (this instanceof LibroDigital) {
	        return TipoLibro.DIGITAL;
	    }
	    return null;
	}
}
