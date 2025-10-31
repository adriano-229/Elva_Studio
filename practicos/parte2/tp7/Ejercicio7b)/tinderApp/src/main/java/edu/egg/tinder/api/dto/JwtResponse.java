package edu.egg.tinder.api.dto;

public class JwtResponse {

	private String token;
	private long expiresAt;
	private UsuarioDto usuario;

	public JwtResponse() {
	}

	public JwtResponse(String token, long expiresAt, UsuarioDto usuario) {
		this.token = token;
		this.expiresAt = expiresAt;
		this.usuario = usuario;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(long expiresAt) {
		this.expiresAt = expiresAt;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}
}
