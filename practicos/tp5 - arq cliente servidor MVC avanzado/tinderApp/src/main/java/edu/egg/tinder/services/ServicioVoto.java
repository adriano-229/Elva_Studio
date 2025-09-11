package edu.egg.tinder.services;

import edu.egg.tinder.entities.Mascota;
import edu.egg.tinder.entities.Voto;
import edu.egg.tinder.error.ErrorServicio;
import edu.egg.tinder.repository.RepositorioMascota;
import edu.egg.tinder.repository.RepositorioVoto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ServicioVoto {

    @Autowired
    private RepositorioVoto repoVoto;

    @Autowired
    private RepositorioMascota repoMascota;

    @Autowired
    private ServicioNotificacion servicioNotificacion;

    // voto desde un perfil de mascota a una mascota
    @Transactional
    public void votar(long usuarioId, long mascota1Id, long mascota2Id) throws ErrorServicio {

        Voto voto = new Voto();
        voto.setFecha(new Date());

        if (mascota1Id == mascota2Id) {
            throw new ErrorServicio("No puede votarse a si mismo");
        }

        Optional<Mascota> respuesta = repoMascota.findById(mascota1Id);

        if (respuesta.isPresent()) {
            Mascota mascota1 = respuesta.get();

            if (mascota1.getUsuario() != null && mascota1.getUsuario().getId().equals(usuarioId)) {
                // voto a la mascota 2
                voto.setMascota1(mascota1);
            } else {
                throw new ErrorServicio("No tiene permiso para realizar la operacion");
            }
        } else {
            throw new ErrorServicio("La mascota no se encuentra");
        }

        Optional<Mascota> respuesta2 = repoMascota.findById(mascota2Id);

        if (respuesta2.isPresent()) {
            Mascota mascota2 = respuesta2.get();
            voto.setMascota2(mascota2);

            servicioNotificacion.enviar("La mascota ha sido votada", "Tinder de Mascota", mascota2.getUsuario().getMail());

        } else {
            throw new ErrorServicio("La mascota a votar no existe");
        }

        repoVoto.save(voto);
    }

    // la mascota votada mira mi perfil y responde (hace match)
    @Transactional
    public void responder(long usuarioId, long votoId) throws ErrorServicio {
        Optional<Voto> respuesta = repoVoto.findById(votoId);

        if (respuesta.isPresent()) {
            Voto voto = respuesta.get();
            voto.setFechaRespuesta(new Date());

            if (voto.getMascota2().getUsuario().getId().equals(usuarioId)) {
                repoVoto.save(voto);

                servicioNotificacion.enviar("El voto fue devuelto!", "Tinder de Mascota", voto.getMascota1().getUsuario().getMail());

            } else {
                throw new ErrorServicio("No tiene permiso para realizar la operacion");
            }

        } else {
            throw new ErrorServicio("No existe el voto solicitado");
        }
    }

}
