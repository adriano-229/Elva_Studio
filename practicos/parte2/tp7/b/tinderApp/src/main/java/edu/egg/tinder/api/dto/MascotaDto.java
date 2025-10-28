package edu.egg.tinder.api.dto;

import edu.egg.tinder.enumeration.Sexo;
import edu.egg.tinder.enumeration.Tipo;

public class MascotaDto {

	private Long id;
	private String nombre;
	private Sexo sexo;
	private Tipo tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
}
