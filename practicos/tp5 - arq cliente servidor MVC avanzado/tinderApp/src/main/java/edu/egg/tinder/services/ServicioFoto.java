package edu.egg.tinder.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.egg.tinder.entities.Foto;
import edu.egg.tinder.repository.RepositorioFoto;
import jakarta.transaction.Transactional;

@Service
public class ServicioFoto {
	
	@Autowired
	private RepositorioFoto repoFoto;
	
	public Foto guardar(MultipartFile archivo) { //multipartfile es donde se va a almacenar el archivo
		if (archivo != null) {
			try {
				Foto foto = new Foto();
				foto.setMime(archivo.getContentType());
				foto.setNombre(archivo.getName());
				foto.setContenido(archivo.getBytes());
				
				return repoFoto.save(foto);
				
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			
		}
		return null;
	}
		
	@Transactional
	public Foto actualizar(Long fotoId, MultipartFile archivo) throws Exception {
		if (archivo != null) {
			try {
				Foto foto = new Foto();
				
				if (foto != null) {
					Optional<Foto> respuesta = repoFoto.findById(fotoId);
					if (respuesta.isPresent()) {
						foto = respuesta.get();
					}
				}
				foto.setMime(archivo.getContentType());
				foto.setNombre(archivo.getName());
				foto.setContenido(archivo.getBytes());
				
				return repoFoto.save(foto);
				
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			
		}
		return null;
	}
}
