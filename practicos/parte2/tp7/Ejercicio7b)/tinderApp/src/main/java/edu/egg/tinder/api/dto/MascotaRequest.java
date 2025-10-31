package edu.egg.tinder.api.dto;

import edu.egg.tinder.enumeration.Sexo;
import edu.egg.tinder.enumeration.Tipo;

public class MascotaRequest {

	private String nombre;
	private Sexo sexo;
	private Tipo tipo;

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
