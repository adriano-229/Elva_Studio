package edu.egg.tinder.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import edu.egg.tinder.api.dto.MascotaDto;
import edu.egg.tinder.api.dto.MascotaRequest;
import edu.egg.tinder.api.mapper.ApiMapper;
import edu.egg.tinder.entities.Mascota;
import edu.egg.tinder.entities.Usuario;
import edu.egg.tinder.error.ErrorServicio;
import edu.egg.tinder.services.ServicioMascota;
import edu.egg.tinder.services.ServicioUsuario;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaApiController {

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private ServicioMascota servicioMascota;

	@GetMapping
	public ResponseEntity<List<MascotaDto>> listar(Authentication authentication) throws ErrorServicio {
		Usuario usuario = servicioUsuario.buscarPorMail(authentication.getName());
		List<Mascota> mascotas = servicioMascota.listarMascotasActivas(usuario.getId());
		return ResponseEntity.ok(ApiMapper.toDto(mascotas));
	}

	@PostMapping
	public ResponseEntity<MascotaDto> crear(@RequestBody MascotaRequest request, Authentication authentication) {
		try {
			Usuario usuario = servicioUsuario.buscarPorMail(authentication.getName());
			Mascota mascota = servicioMascota.crearMascotaApi(usuario.getId(), request.getNombre(), request.getSexo(),
					request.getTipo());
			return ResponseEntity.status(HttpStatus.CREATED).body(ApiMapper.toDto(mascota));
		} catch (ErrorServicio e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<MascotaDto> actualizar(@PathVariable Long id, @RequestBody MascotaRequest request,
			Authentication authentication) {
		try {
			Usuario usuario = servicioUsuario.buscarPorMail(authentication.getName());
			Mascota mascota = servicioMascota.actualizarMascotaApi(usuario.getId(), id, request.getNombre(),
					request.getSexo(), request.getTipo());
			return ResponseEntity.ok(ApiMapper.toDto(mascota));
		} catch (ErrorServicio e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id, Authentication authentication) {
		try {
			Usuario usuario = servicioUsuario.buscarPorMail(authentication.getName());
			servicioMascota.eliminarMascota(usuario.getId(), id);
			return ResponseEntity.noContent().build();
		} catch (ErrorServicio e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}
}
