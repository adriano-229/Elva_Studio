package edu.egg.tinder.api.dto;

public class UsuarioUpdateRequest {

	private String nombre;
	private String apellido;
	private String mail;
	private Long zonaId;
	private String clave;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Long getZonaId() {
		return zonaId;
	}

	public void setZonaId(Long zonaId) {
		this.zonaId = zonaId;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
}
