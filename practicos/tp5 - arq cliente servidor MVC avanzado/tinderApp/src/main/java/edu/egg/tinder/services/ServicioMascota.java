package edu.egg.tinder.services;

import edu.egg.tinder.entities.Foto;
import edu.egg.tinder.entities.Mascota;
import edu.egg.tinder.entities.Usuario;
import edu.egg.tinder.enumeration.Sexo;
import edu.egg.tinder.error.ErrorServicio;
import edu.egg.tinder.repository.RepositorioMascota;
import edu.egg.tinder.repository.RepositorioUsuario;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Optional;

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
    public void agregarMascota(MultipartFile archivo, long usuarioId, String nombre, Sexo sexo) throws ErrorServicio {
        Optional<Usuario> respuesta = repoUsuario.findById(usuarioId);
        verificar(nombre, sexo);

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            Mascota mascota = new Mascota();
            mascota.setNombre(nombre);
            mascota.setSexo(sexo);
            mascota.setAlta(new Date());
            mascota.setUsuario(usuario);

            Foto foto = servicioFoto.guardar(archivo);
            mascota.setFoto(foto);

            repoMascota.save(mascota);
        } else {
            throw new ErrorServicio("El usuario no fue encontrado");
        }
    }

    // modificar una mascota
    @Transactional
    public void modificarMascota(MultipartFile archivo, long usuarioId, long mascotaId, String nombre, Sexo sexo) throws ErrorServicio {
        Optional<Mascota> respuesta = repoMascota.findById(mascotaId);
        verificar(nombre, sexo);

        if (respuesta.isPresent()) {
            Mascota mascota = respuesta.get();
            if (mascota.getUsuario() != null && mascota.getUsuario().getId().equals(usuarioId)) {
                mascota.setNombre(nombre);
                mascota.setSexo(sexo);

                Long fotoId = null;
                if (mascota.getFoto() != null) {
                    fotoId = mascota.getFoto().getId();
                }
                try {
                    Foto foto = servicioFoto.actualizar(fotoId, archivo);
                    mascota.setFoto(foto);

                    repoMascota.save(mascota);
                } catch (Exception e) {
                    throw new ErrorServicio("No se pudo actualizar la foto");
                }
            } else {
                throw new ErrorServicio("El usuario no tiene permiso de modificar la mascota");
            }
        } else {
            throw new ErrorServicio("No existe una mascota con el id solicitado");
        }
    }

    @Transactional
    public void eliminarMascota(long usuarioId, long mascotaId) throws ErrorServicio {
        Optional<Mascota> respuesta = repoMascota.findById(mascotaId);

        if (respuesta.isPresent()) {
            Mascota mascota = respuesta.get();
            if (mascota.getUsuario() != null && mascota.getUsuario().getId().equals(usuarioId)) {
                mascota.setBaja(new Date());
                repoMascota.save(mascota);
            } else {
                throw new ErrorServicio("No tiene permisos de realizar la eliminacion");
            }
        } else {
            throw new ErrorServicio("No se encontro la mascota");
        }
    }

    //Verificaciones
    @Transactional
    public void verificar(String nombre, Sexo sexo) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede ser nulo");
        }
        if (sexo == null) {
            throw new ErrorServicio("El sexo no puede ser nulo");
        }
    }
}
