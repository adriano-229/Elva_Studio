package edu.egg.tinder.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.egg.tinder.api.dto.JwtResponse;
import edu.egg.tinder.api.dto.LoginRequest;
import edu.egg.tinder.api.dto.ErrorResponse;
import edu.egg.tinder.api.mapper.ApiMapper;
import edu.egg.tinder.security.jwt.JwtTokenProvider;
import edu.egg.tinder.services.ServicioUsuario;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		if (request.getEmail() == null || request.getEmail().isBlank() || request.getClave() == null
				|| request.getClave().isBlank()) {
			return ResponseEntity.badRequest()
					.body(ErrorResponse.of(HttpStatus.BAD_REQUEST, "Debe enviar email y clave"));
		}
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getEmail(), request.getClave()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			UserDetails principal = (UserDetails) authentication.getPrincipal();
			String token = jwtTokenProvider.generateToken(principal);
			long expiresAt = jwtTokenProvider.getExpirationInstant(token).toEpochMilli();

			return ResponseEntity.ok(new JwtResponse(token, expiresAt,
					ApiMapper.toDto(servicioUsuario.buscarPorMail(principal.getUsername()))));

		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ErrorResponse.of(HttpStatus.UNAUTHORIZED, "Email o clave incorrectos"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo completar el login"));
		}
	}
}
