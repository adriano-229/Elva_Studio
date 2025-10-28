package edu.egg.tinder.api.dto;

public class UsuarioDto {

	private Long id;
	private String nombre;
	private String apellido;
	private String mail;
	private Long zonaId;
	private String zonaNombre;

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

	public String getZonaNombre() {
		return zonaNombre;
	}

	public void setZonaNombre(String zonaNombre) {
		this.zonaNombre = zonaNombre;
	}
}
