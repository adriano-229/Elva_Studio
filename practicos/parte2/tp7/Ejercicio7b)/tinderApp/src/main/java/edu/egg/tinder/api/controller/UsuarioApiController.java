package edu.egg.tinder.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import edu.egg.tinder.api.dto.UsuarioDto;
import edu.egg.tinder.api.dto.UsuarioUpdateRequest;
import edu.egg.tinder.api.mapper.ApiMapper;
import edu.egg.tinder.entities.Usuario;
import edu.egg.tinder.error.ErrorServicio;
import edu.egg.tinder.services.ServicioUsuario;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioApiController {

	@Autowired
	private ServicioUsuario servicioUsuario;

	@GetMapping("/me")
	public ResponseEntity<UsuarioDto> me(Authentication authentication) {
		Usuario usuario = servicioUsuario.buscarPorMail(authentication.getName());
		return ResponseEntity.ok(ApiMapper.toDto(usuario));
	}

	@PutMapping("/me")
	public ResponseEntity<UsuarioDto> actualizar(@RequestBody UsuarioUpdateRequest request, Authentication authentication) {
		try {
			Usuario usuarioActualizado = servicioUsuario.actualizarDatosBasicos(authentication.getName(), request.getNombre(),
					request.getApellido(), request.getMail(), request.getZonaId(), request.getClave());
			return ResponseEntity.ok(ApiMapper.toDto(usuarioActualizado));
		} catch (ErrorServicio e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}
}
