package edu.egg.tinder.api.mapper;

import java.util.ArrayList;
import java.util.List;

import edu.egg.tinder.api.dto.MascotaDto;
import edu.egg.tinder.api.dto.UsuarioDto;
import edu.egg.tinder.entities.Mascota;
import edu.egg.tinder.entities.Usuario;
import edu.egg.tinder.entities.Zona;

public final class ApiMapper {

	private ApiMapper() {
	}

	public static UsuarioDto toDto(Usuario usuario) {
		if (usuario == null) {
			return null;
		}
		UsuarioDto dto = new UsuarioDto();
		dto.setId(usuario.getId());
		dto.setNombre(usuario.getNombre());
		dto.setApellido(usuario.getApellido());
		dto.setMail(usuario.getMail());

		Zona zona = usuario.getZona();
		if (zona != null) {
			dto.setZonaId(zona.getId());
			dto.setZonaNombre(zona.getNombre());
		}
		return dto;
	}

	public static MascotaDto toDto(Mascota mascota) {
		if (mascota == null) {
			return null;
		}
		MascotaDto dto = new MascotaDto();
		dto.setId(mascota.getId());
		dto.setNombre(mascota.getNombre());
		dto.setSexo(mascota.getSexo());
		dto.setTipo(mascota.getTipo());
		return dto;
	}

	public static List<MascotaDto> toDto(List<Mascota> mascotas) {
		List<MascotaDto> resultado = new ArrayList<>();
		if (mascotas != null) {
			for (Mascota mascota : mascotas) {
				resultado.add(toDto(mascota));
			}
		}
		return resultado;
	}
}
