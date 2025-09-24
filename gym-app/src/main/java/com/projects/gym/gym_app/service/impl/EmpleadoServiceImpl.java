package com.projects.gym.gym_app.service.impl;

import com.projects.gym.gym_app.domain.Empleado;
import com.projects.gym.gym_app.domain.Sucursal;
import com.projects.gym.gym_app.domain.Direccion;
import com.projects.gym.gym_app.domain.Localidad;
import com.projects.gym.gym_app.domain.Usuario;
import com.projects.gym.gym_app.domain.enums.Rol;
import com.projects.gym.gym_app.domain.enums.TipoEmpleado;
import com.projects.gym.gym_app.repository.EmpleadoRepository;
import com.projects.gym.gym_app.repository.SucursalRepository;
import com.projects.gym.gym_app.repository.DireccionRepository;
import com.projects.gym.gym_app.repository.LocalidadRepository;
import com.projects.gym.gym_app.repository.UsuarioRepository;
import com.projects.gym.gym_app.service.EmpleadoService;
import com.projects.gym.gym_app.service.dto.EmpleadoDTO;
import com.projects.gym.gym_app.service.dto.EmpleadoFormDTO;
import com.projects.gym.gym_app.service.dto.EmpleadoListadoDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final SucursalRepository sucursalRepository;
    private final DireccionRepository direccionRepository;
    private final LocalidadRepository localidadRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<EmpleadoDTO> listarProfesoresActivos() {
        Comparator<String> stringComparator = Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER);

        return empleadoRepository
                .findByActivoTrueAndTipo(TipoEmpleado.PROFESOR)
                .stream()
                .sorted(Comparator.comparing(Empleado::getApellido, stringComparator)
                        .thenComparing(Empleado::getNombre, stringComparator))
                .map(this::toEmpleadoDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmpleadoDTO> buscarProfesorActivoPorUsuario(String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            return Optional.empty();
        }

        return empleadoRepository.findByUsuario_NombreUsuarioAndActivoTrue(nombreUsuario)
                .filter(emp -> emp.getTipo() == TipoEmpleado.PROFESOR)
                .map(this::toEmpleadoDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmpleadoListadoDTO> listar(String filtro, TipoEmpleado tipo) {
        String criterio = filtro != null ? filtro.trim().toLowerCase(Locale.ROOT) : null;
        Comparator<String> stringComparator = Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER);

        return empleadoRepository.findAll().stream()
                .filter(emp -> tipo == null || emp.getTipo() == tipo)
                .sorted(Comparator.comparing(Empleado::getApellido, stringComparator)
                        .thenComparing(Empleado::getNombre, stringComparator))
                .filter(emp -> coincideFiltro(emp, criterio))
                .map(this::toListadoDto)
                .toList();
    }

    @Override
    public EmpleadoFormDTO crear(EmpleadoFormDTO form) {
        String username = limpiar(form.getNombreUsuario());
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }
        if (form.getRol() == null || form.getRol().isBlank()) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }
        String password = form.getClave();
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("La clave es obligatoria");
        }
        asegurarNombreUsuarioDisponible(username, null);

        if (form.getTipo() == null && form.getRol() != null && !form.getRol().isBlank()) {
            form.setTipo(TipoEmpleado.valueOf(form.getRol()));
        }
        Empleado empleado = new Empleado();
        mapearPersona(empleado, form);
        empleado.setActivo(form.isActivo());
        empleado.setTipo(form.getTipo());
        empleado.setEliminado(!form.isActivo());

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(username);
        usuario.setClave(passwordEncoder.encode(password));
        usuario.setRol(mapRol(form.getTipo()));
        usuario.setEliminado(!form.isActivo());
        empleado.setUsuario(usuario);

        Empleado guardado = empleadoRepository.save(empleado);
        return toFormDto(guardado);
    }

    @Override
    public EmpleadoFormDTO actualizar(Long id, EmpleadoFormDTO form) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        String username = limpiar(form.getNombreUsuario());
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }
        asegurarNombreUsuarioDisponible(username, empleado.getUsuario() != null ? empleado.getUsuario().getId() : null);
        if (form.getRol() == null || form.getRol().isBlank()) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }

        mapearPersona(empleado, form);
        empleado.setTipo(form.getTipo());
        empleado.setActivo(form.isActivo());
        empleado.setEliminado(!form.isActivo());

        Usuario usuario = empleado.getUsuario();
        if (usuario == null) {
            usuario = new Usuario();
        }
        usuario.setNombreUsuario(username);
        if (form.getClave() != null && !form.getClave().isBlank()) {
            usuario.setClave(passwordEncoder.encode(form.getClave()));
        }
        usuario.setRol(mapRol(form.getTipo()));
        usuario.setEliminado(!form.isActivo());
        empleado.setUsuario(usuario);

        Empleado actualizado = empleadoRepository.save(empleado);
        return toFormDto(actualizado);
    }

    @Override
    @Transactional(readOnly = true)
    public EmpleadoFormDTO buscarPorId(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));
        return toFormDto(empleado);
    }

    @Override
    public void cambiarEstado(Long id, boolean activo) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));
        empleado.setActivo(activo);
        empleado.setEliminado(!activo);
        if (empleado.getUsuario() != null) {
            empleado.getUsuario().setEliminado(!activo);
        }
        empleadoRepository.save(empleado);
    }

    private boolean coincideFiltro(Empleado empleado, String filtro) {
        if (filtro == null || filtro.isBlank()) {
            return true;
        }
        String nombreCompleto = (empleado.getNombre() + " " + empleado.getApellido()).toLowerCase(Locale.ROOT);
        String username = empleado.getUsuario() != null ? empleado.getUsuario().getNombreUsuario().toLowerCase(Locale.ROOT) : "";
        String documento = empleado.getNumeroDocumento() != null ? empleado.getNumeroDocumento().toLowerCase(Locale.ROOT) : "";
        return nombreCompleto.contains(filtro) || username.contains(filtro) || documento.contains(filtro);
    }

    private EmpleadoListadoDTO toListadoDto(Empleado empleado) {
        return EmpleadoListadoDTO.builder()
                .id(empleado.getId())
                .nombreCompleto(empleado.getApellido() + ", " + empleado.getNombre())
                .nombreUsuario(empleado.getUsuario() != null ? empleado.getUsuario().getNombreUsuario() : null)
                .tipo(empleado.getTipo())
                .activo(empleado.isActivo())
                .telefono(empleado.getTelefono())
                .correoElectronico(empleado.getCorreoElectronico())
                .sucursalNombre(empleado.getSucursal() != null ? empleado.getSucursal().getNombre() : null)
                .build();
    }

    private EmpleadoFormDTO toFormDto(Empleado empleado) {
        EmpleadoFormDTO dto = new EmpleadoFormDTO();
        dto.setId(empleado.getId());
        dto.setNombre(empleado.getNombre());
        dto.setApellido(empleado.getApellido());
        dto.setFechaNacimiento(empleado.getFechaNacimiento());
        dto.setTipoDocumento(empleado.getTipoDocumento());
        dto.setNumeroDocumento(empleado.getNumeroDocumento());
        dto.setTelefono(empleado.getTelefono());
        dto.setCorreoElectronico(empleado.getCorreoElectronico());
        dto.setTipo(empleado.getTipo());
        dto.setActivo(empleado.isActivo());
        if (empleado.getSucursal() != null) {
            dto.setSucursalId(empleado.getSucursal().getId());
            dto.setSucursalNombre(empleado.getSucursal().getNombre());
        }
        Usuario usuario = empleado.getUsuario();
        if (usuario != null) {
            dto.setUsuarioId(usuario.getId());
            dto.setNombreUsuario(usuario.getNombreUsuario());
        }
        Direccion direccion = empleado.getDireccion();
        if (direccion != null) {
            dto.setDireccionId(direccion.getId());
            dto.setCalle(direccion.getCalle());
            dto.setNumeracion(direccion.getNumeracion());
            dto.setBarrio(direccion.getBarrio());
            dto.setManzanaPiso(direccion.getManzanaPiso());
            dto.setCasaDepartamento(direccion.getCasaDepartamento());
            dto.setReferencia(direccion.getReferencia());
            Localidad loc = direccion.getLocalidad();
            if (loc != null) {
                dto.setLocalidadId(loc.getId());
            }
        }
        dto.setRol(empleado.getTipo() != null ? empleado.getTipo().name() : null);
        return dto;
    }

    private void mapearPersona(Empleado empleado, EmpleadoFormDTO form) {
        empleado.setNombre(form.getNombre());
        empleado.setApellido(form.getApellido());
        empleado.setFechaNacimiento(form.getFechaNacimiento());
        empleado.setTipoDocumento(form.getTipoDocumento());
        empleado.setNumeroDocumento(form.getNumeroDocumento());
        empleado.setTelefono(form.getTelefono());
        empleado.setCorreoElectronico(form.getCorreoElectronico());
        empleado.setEliminado(!form.isActivo());

        Sucursal sucursal = sucursalRepository.findById(form.getSucursalId())
                .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada"));
        empleado.setSucursal(sucursal);

        if (form.getLocalidadId() == null || form.getLocalidadId().isBlank()) {
            throw new IllegalArgumentException("La localidad es obligatoria");
        }
        Localidad localidad = localidadRepository.findById(form.getLocalidadId())
                .orElseThrow(() -> new EntityNotFoundException("Localidad no encontrada"));

        Direccion direccion;
        if (form.getDireccionId() != null && !form.getDireccionId().isBlank()) {
            direccion = direccionRepository.findById(form.getDireccionId())
                    .orElseThrow(() -> new EntityNotFoundException("Dirección no encontrada"));
        } else {
            direccion = empleado.getDireccion();
            if (direccion == null) {
                direccion = new Direccion();
            }
        }

        direccion.setCalle(form.getCalle());
        direccion.setNumeracion(form.getNumeracion());
        direccion.setBarrio(form.getBarrio());
        direccion.setManzanaPiso(form.getManzanaPiso());
        direccion.setCasaDepartamento(form.getCasaDepartamento());
        direccion.setReferencia(form.getReferencia());
        direccion.setEliminado(false);
        direccion.setLocalidad(localidad);

        empleado.setDireccion(direccionRepository.save(direccion));
    }

    private void asegurarNombreUsuarioDisponible(String username, String usuarioIdActual) {
        usuarioRepository.findByNombreUsuario(username)
                .filter(usuario -> !usuario.getId().equals(usuarioIdActual))
                .ifPresent(u -> {
                    throw new IllegalArgumentException("El nombre de usuario ya está en uso");
                });
    }

    private String limpiar(String valor) {
        return valor != null ? valor.trim() : null;
    }

    private Rol mapRol(TipoEmpleado tipo) {
        return tipo == TipoEmpleado.PROFESOR ? Rol.PROFESOR : Rol.OPERADOR;
    }

    private EmpleadoDTO toEmpleadoDto(Empleado emp) {
        return EmpleadoDTO.builder()
                .id(emp.getId())
                .nombreCompleto(emp.getApellido() + ", " + emp.getNombre())
                .tipo(emp.getTipo())
                .activo(emp.isActivo())
                .build();
    }
}
