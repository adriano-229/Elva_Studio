package edu.egg.tinder.services;

import edu.egg.tinder.entities.Foto;
import edu.egg.tinder.entities.Usuario;
import edu.egg.tinder.entities.Zona;
import edu.egg.tinder.error.ErrorServicio;
import edu.egg.tinder.repository.RepositorioUsuario;
import edu.egg.tinder.repository.RepositorioZona;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Optional;

@Service
public class ServicioUsuario {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private ServicioNotificacion servicioNotificacion;

    @Autowired
    private ServicioFoto servicioFoto;

    @Autowired
    private RepositorioZona repoZona;

    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String apellido, String mail, String clave1, String clave2, Long idZona) throws ErrorServicio {
        Zona zona = repoZona.findById(idZona).orElseThrow(() -> new ErrorServicio("La zona indicada no existe"));
        verificar(nombre, apellido, mail, clave1, clave2, zona);

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setClave(clave1);
        usuario.setAlta(new Date());
        usuario.setZona(zona);

        Foto foto = servicioFoto.guardar(archivo);
        usuario.setFoto(foto);

        repositorioUsuario.save(usuario);
        // servicioNotificacion.enviar("Bienvenido al Tinder de Mascotas", "Tinder de Mascota", usuario.getMail());
    }

    @Transactional
    public void editar(MultipartFile archivo, long usuarioId, String nombre, String apellido, String mail, String clave1, String clave2, long idZona) throws ErrorServicio {
        Zona zona = repoZona.findById(idZona).orElseThrow(() -> new ErrorServicio("La zona indicada no existe"));
        verificar(nombre, apellido, mail, clave1, clave2, zona);

        Optional<Usuario> respuesta = repositorioUsuario.findById(usuarioId);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setApellido(apellido);
            usuario.setMail(mail);
            usuario.setNombre(nombre);
            usuario.setClave(clave1);
            usuario.setZona(zona);

            Long fotoId = (usuario.getFoto() != null) ? usuario.getFoto().getId() : null;
            try {
                Foto foto = servicioFoto.actualizar(fotoId, archivo);
                usuario.setFoto(foto);
                repositorioUsuario.save(usuario);
            } catch (Exception e) {
                throw new ErrorServicio("No se pudo actualizar la foto");
            }
        } else {
            throw new ErrorServicio("No se encontró el usuario");
        }
    }

    @Transactional
    public void deshabilitar(Long id) throws ErrorServicio {
        Optional<Usuario> respuesta = repositorioUsuario.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setBaja(new Date());
            repositorioUsuario.save(usuario);
        } else {
            throw new ErrorServicio("El usuario no fue encontrado");
        }
    }

    @Transactional
    public void habilitar(Long id) throws ErrorServicio {
        Optional<Usuario> respuesta = repositorioUsuario.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setAlta(new Date());
            repositorioUsuario.save(usuario);
        } else {
            throw new ErrorServicio("El usuario no fue encontrado");
        }
    }

    @Transactional
    public boolean validarLogin(Usuario usuario, String email, String clave) {
        return usuario.getMail().equals(email) && usuario.getClave().equals(clave);
    }

    public void verificar(String nombre, String apellido, String mail, String clave1, String clave2, Zona zona) throws ErrorServicio {
        if (nombre == null || nombre.isBlank()) {
            throw new ErrorServicio("El nombre no puede ser nulo");
        }
        if (apellido == null || apellido.isBlank()) {
            throw new ErrorServicio("El apellido no puede ser nulo");
        }
        if (mail == null || mail.isBlank()) {
            throw new ErrorServicio("El mail no puede ser nulo");
        }
        if (clave1 == null || clave1.length() < 6) {
            throw new ErrorServicio("La clave no puede ser nula ni menor a 6 dígitos");
        }
        if (!clave1.equals(clave2)) {
            throw new ErrorServicio("Las claves deben ser iguales");
        }
        if (zona == null) {
            throw new ErrorServicio("La zona no puede estar vacía");
        }
    }
}
