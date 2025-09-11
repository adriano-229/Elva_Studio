package edu.egg.tinder.services;

import edu.egg.tinder.entities.Foto;
import edu.egg.tinder.repository.RepositorioFoto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class ServicioFoto {

    @Autowired
    private RepositorioFoto repoFoto;

    public Foto guardar(MultipartFile archivo) {
        if (archivo != null && !archivo.isEmpty()) {
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getOriginalFilename());
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
        if (archivo != null && !archivo.isEmpty()) {
            try {
                Foto foto;
                if (fotoId != null) {
                    Optional<Foto> respuesta = repoFoto.findById(fotoId);
                    foto = respuesta.orElseGet(Foto::new);
                } else {
                    foto = new Foto();
                }
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getOriginalFilename());
                foto.setContenido(archivo.getBytes());
                return repoFoto.save(foto);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                throw e;
            }
        }
        return null;
    }
}
