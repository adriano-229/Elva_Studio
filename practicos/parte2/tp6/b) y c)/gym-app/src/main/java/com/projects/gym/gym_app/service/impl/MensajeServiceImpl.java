package com.projects.gym.gym_app.service.impl;

import com.projects.gym.gym_app.domain.Mensaje;
import com.projects.gym.gym_app.domain.Promocion;
import com.projects.gym.gym_app.domain.Socio;
import com.projects.gym.gym_app.domain.Usuario;
import com.projects.gym.gym_app.domain.enums.Rol;
import com.projects.gym.gym_app.domain.enums.TipoMensaje;
import com.projects.gym.gym_app.repository.MensajeRepository;
import com.projects.gym.gym_app.repository.SocioRepository;
import com.projects.gym.gym_app.repository.UsuarioRepository;
import com.projects.gym.gym_app.service.MensajeService;
import com.projects.gym.gym_app.service.dto.EnvioMensajeCommand;
import com.projects.gym.gym_app.service.dto.EnvioPromocionCommand;
import com.projects.gym.gym_app.service.dto.MensajeFormDTO;
import com.projects.gym.gym_app.service.sender.NotificationSender;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MensajeServiceImpl implements MensajeService {

    private final MensajeRepository mensajeRepo;
    private final UsuarioRepository usuarioRepo;
    private final SocioRepository socioRepo;
    private final NotificationSender sender;

    @Transactional(readOnly = true)
    @Override
    public Page<MensajeFormDTO> listar(Pageable pageable) {
        return mensajeRepo.findByEliminadoFalse(pageable).map(this::toDto);
    }

    @Override
    public MensajeFormDTO crear(MensajeFormDTO d) {
        Usuario autor = obtenerAdministrador();
        Mensaje mensaje = esMensajeMasivo(d.getTipoMensaje()) ? nuevaPromocion(d, autor) : nuevoMensajeBasico(d, autor);
        return toDto(mensajeRepo.save(mensaje));
    }

    @Override
    public MensajeFormDTO editar(String id, MensajeFormDTO d) {
        Mensaje mensaje = mensajeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mensaje no encontrado"));

        mensaje.setTitulo(d.getTitulo());
        mensaje.setTexto(d.getTexto());
        mensaje.setTipoMensaje(d.getTipoMensaje());

        if (mensaje instanceof Promocion promocion && !esMensajeMasivo(d.getTipoMensaje())) {
            promocion.setFechaEnvioPromocion(null);
            promocion.setCantidadSociosEnviados(0L);
        }

        return toDto(mensaje);
    }

    @Override
    public void eliminar(String id) {
        Mensaje mensaje = mensajeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mensaje no encontrado"));
        mensaje.setEliminado(true);
    }

    @Transactional(readOnly = true)
    @Override
    public MensajeFormDTO buscar(String id) {
        return mensajeRepo.findById(id).map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Mensaje no encontrado"));
    }

    @Override
    public void enviarIndividual(String idMensaje, EnvioMensajeCommand cmd) {
        Mensaje mensaje = mensajeRepo.findById(idMensaje)
                .orElseThrow(() -> new EntityNotFoundException("Mensaje no encontrado"));

        Socio socio = socioRepo.findById(cmd.getSocioId())
                .orElseThrow(() -> new EntityNotFoundException("Socio no encontrado"));

        if (socio.getCorreoElectronico() != null && !socio.getCorreoElectronico().isBlank()) {
            sender.sendToSocio(socio, mensaje.getTitulo(), mensaje.getTexto());
        }
    }

    @Override
    public int enviarMasivo(String idMensaje, EnvioPromocionCommand cmd) {
        Mensaje mensaje = mensajeRepo.findById(idMensaje)
                .orElseThrow(() -> new EntityNotFoundException("Mensaje no encontrado"));

        if (!(mensaje instanceof Promocion promocion)) {
            throw new IllegalArgumentException("El mensaje indicado no admite envío masivo");
        }

        List<Socio> destinatarios = switch (cmd.getAlcance().toUpperCase()) {
            case "TODOS" -> sociosActivos();
            case "CUMPLEANIOS", "CUMPLEAÑOS" -> sociosCumpleaniosHoy();
            default -> throw new IllegalArgumentException("Alcance no soportado: " + cmd.getAlcance());
        };

        int enviados = 0;
        for (Socio socio : destinatarios) {
            if (socio.getCorreoElectronico() != null && !socio.getCorreoElectronico().isBlank()) {
                sender.sendToSocio(socio, promocion.getTitulo(), promocion.getTexto());
                enviados++;
            }
        }

        promocion.setFechaEnvioPromocion(LocalDate.now());
        long acumulado = promocion.getCantidadSociosEnviados() != null ? promocion.getCantidadSociosEnviados() : 0L;
        promocion.setCantidadSociosEnviados(acumulado + enviados);

        return enviados;
    }

    private Mensaje nuevoMensajeBasico(MensajeFormDTO d, Usuario autor) {
        Mensaje mensaje = new Mensaje();
        mensaje.setUsuario(autor);
        mensaje.setTitulo(d.getTitulo());
        mensaje.setTexto(d.getTexto());
        mensaje.setTipoMensaje(d.getTipoMensaje());
        mensaje.setEliminado(false);
        return mensaje;
    }

    private Mensaje nuevaPromocion(MensajeFormDTO d, Usuario autor) {
        Promocion promocion = new Promocion();
        promocion.setUsuario(autor);
        promocion.setTitulo(d.getTitulo());
        promocion.setTexto(d.getTexto());
        promocion.setTipoMensaje(d.getTipoMensaje());
        promocion.setEliminado(false);
        promocion.setCantidadSociosEnviados(0L);
        return promocion;
    }

    private MensajeFormDTO toDto(Mensaje mensaje) {
        MensajeFormDTO dto = new MensajeFormDTO();
        dto.setId(mensaje.getId());
        dto.setTitulo(mensaje.getTitulo());
        dto.setTexto(mensaje.getTexto());
        dto.setTipoMensaje(mensaje.getTipoMensaje());
        dto.setAutorNombre(mensaje.getUsuario() != null ? mensaje.getUsuario().getNombreUsuario() : null);

        if (mensaje instanceof Promocion promocion) {
            dto.setFechaEnvioPromocion(promocion.getFechaEnvioPromocion());
            dto.setCantidadSociosEnviados(promocion.getCantidadSociosEnviados());
        }
        return dto;
    }

    private Usuario obtenerAdministrador() {
        return usuarioRepo.findFirstByRolAndEliminadoFalse(Rol.ADMIN)
                .orElseThrow(() -> new IllegalStateException("No se encontró un usuario administrador activo"));
    }

    private boolean esMensajeMasivo(TipoMensaje tipo) {
        return tipo == TipoMensaje.PROMOCION
                || tipo == TipoMensaje.CUMPLEANIOS
                || tipo == TipoMensaje.OTROS;
    }

    private List<Socio> sociosActivos() {
        return socioRepo.findByEliminadoFalse(Pageable.unpaged()).getContent();
    }

    private List<Socio> sociosCumpleaniosHoy() {
        MonthDay hoy = MonthDay.from(LocalDate.now());
        return sociosActivos().stream()
                .filter(s -> s.getFechaNacimiento() != null)
                .filter(s -> MonthDay.from(s.getFechaNacimiento()).equals(hoy))
                .toList();
    }
}
