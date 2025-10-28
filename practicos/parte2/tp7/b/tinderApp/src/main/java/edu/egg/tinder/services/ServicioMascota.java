package edu.egg.tinder.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.egg.tinder.entities.Foto;
import edu.egg.tinder.entities.Mascota;
import edu.egg.tinder.entities.Usuario;
import edu.egg.tinder.enumeration.Sexo;
import edu.egg.tinder.enumeration.Tipo;
import edu.egg.tinder.error.ErrorServicio;
import edu.egg.tinder.repository.RepositorioMascota;
import edu.egg.tinder.repository.RepositorioUsuario;
import jakarta.transaction.Transactional;

@Service
public class ServicioMascota {
	
	@Autowired
	private RepositorioUsuario repoUsuario;
	
	@Autowired
	private RepositorioMascota repoMascota;
	
	@Autowired
	private ServicioFoto servicioFoto; 
	
	//agregar mascota
	@Transactional
	public void agregarMascota(MultipartFile archivo, Long usuarioId, String nombre, Sexo sexo, Tipo tipo) throws ErrorServicio {
		
		//veo que este el usuario
		Optional<Usuario> respuesta = repoUsuario.findById(usuarioId);
		verificar(nombre, sexo,tipo);
		
		if (respuesta.isPresent()) {
			Usuario usuario = respuesta.get();
			Mascota mascota = new Mascota();
			mascota.setUsuario(usuario);			
			mascota.setNombre(nombre);
			mascota.setSexo(sexo);
			mascota.setAlta(new Date());
			mascota.setTipo(tipo);
			
			Foto foto = servicioFoto.guardar(archivo);
			mascota.setFoto(foto);
			
			repoMascota.save(mascota);
			
		} else {
			throw new ErrorServicio("El usuario no fue encontrado");
		}	
	}
	
	// modificar una mascota
	@Transactional
	public void modificarMascota(MultipartFile archivo, Long usuarioId, Long mascotaId, String nombre, Sexo sexo, Tipo tipo) throws ErrorServicio {
		
		//busco la mascota
		Optional<Mascota> respuesta = repoMascota.findById(mascotaId);
		
		//verifico los parametros a modificar
		verificar(nombre,sexo,tipo);
		
		if (respuesta.isPresent()) {
			Mascota mascota = respuesta.get();
			if (mascota.getUsuario() != null && mascota.getUsuario().getId().equals(usuarioId)) {
				mascota.setNombre(nombre);
				mascota.setSexo(sexo);
				mascota.setTipo(tipo);
				
				Long fotoId = null;
				
				if (mascota.getFoto() != null) {
					fotoId = mascota.getFoto().getId();
				}
				try {
					Foto foto = servicioFoto.actualizar(fotoId, archivo);
					mascota.setFoto(foto);
					
					repoMascota.save(mascota);
				}catch (Exception e) {
					throw new ErrorServicio("No se pudo actulizar la foto");
				}
			} else {
				throw new ErrorServicio("El usuario no tiene permiso de modificar la mascota");
			}
			
		} else {
			throw new ErrorServicio("No existe una mascota con el id solicitado");
		}
		
	}
	
	@Transactional
	public void eliminarMascota(long usuarioId, long mascotaId) throws ErrorServicio{
		
		Optional<Mascota> respuesta = repoMascota.findById(mascotaId);
		
		if (respuesta.isPresent()) {
			Mascota mascota = respuesta.get();
			if (mascota.getUsuario() != null && mascota.getUsuario().getId().equals(usuarioId)) {
				mascota.setBaja(new Date());
				repoMascota.save(mascota);
			} else {
				throw new ErrorServicio("No tiene permisos de realizar la eliminacion");
			}
			
		}else {
			throw new ErrorServicio("No se encontro la mascota");
		}
	}
	
	// buscar mascota por id
	public Mascota buscarMascotaPorId(Long id) throws ErrorServicio {
		Optional<Mascota> respuesta = repoMascota.findById(id);
		
		if (respuesta.isPresent()) {
			Mascota mascota = respuesta.get();
			return mascota;
		} else {
			throw new ErrorServicio("No se encontro la mascota");
		}
	}
	
	// buscar mascota por usuario
	public List<Mascota> buscarMascotaPorUsuario(Long idUsuario){
		return repoMascota.buscarMascotaPorUsuario(idUsuario);
	}
	
	@Transactional
	public List<Mascota> listarMascotasActivas(Long idUsuario) {
		return repoMascota.buscarMascotaPorUsuario(idUsuario);
	}
	
	@Transactional
	public Mascota crearMascotaApi(Long usuarioId, String nombre, Sexo sexo, Tipo tipo) throws ErrorServicio {
		Optional<Usuario> respuesta = repoUsuario.findById(usuarioId);
		verificar(nombre, sexo, tipo);

		if (respuesta.isPresent()) {
			Usuario usuario = respuesta.get();
			Mascota mascota = new Mascota();
			mascota.setUsuario(usuario);
			mascota.setNombre(nombre);
			mascota.setSexo(sexo);
			mascota.setTipo(tipo);
			mascota.setAlta(new Date());
			return repoMascota.save(mascota);
		} else {
			throw new ErrorServicio("El usuario no fue encontrado");
		}
	}
	
	@Transactional
	public Mascota actualizarMascotaApi(Long usuarioId, Long mascotaId, String nombre, Sexo sexo, Tipo tipo) throws ErrorServicio {
		Optional<Mascota> respuesta = repoMascota.findById(mascotaId);
		verificar(nombre, sexo, tipo);

		if (respuesta.isPresent()) {
			Mascota mascota = respuesta.get();
			if (mascota.getUsuario() != null && mascota.getUsuario().getId().equals(usuarioId)) {
				mascota.setNombre(nombre);
				mascota.setSexo(sexo);
				mascota.setTipo(tipo);
				return repoMascota.save(mascota);
			} else {
				throw new ErrorServicio("El usuario no tiene permiso de modificar la mascota");
			}
		}
		throw new ErrorServicio("No existe una mascota con el id solicitado");
	}
		
	//Verificaciones
	@Transactional
	public void verificar(String nombre, Sexo sexo, Tipo tipo) throws ErrorServicio{
		if (nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio("El usuario no puede ser nulo");
		}
		if (sexo == null) {
			throw new ErrorServicio("El sexo no puede ser nulo");
		}
		if (tipo == null) {
			throw new ErrorServicio("El tipo no puede ser nulo");
		}
		
	}
}
