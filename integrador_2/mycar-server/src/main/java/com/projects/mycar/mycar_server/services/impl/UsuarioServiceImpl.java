package com.projects.mycar.mycar_server.services.impl;

import com.projects.mycar.mycar_server.dto.UsuarioDTO;
import com.projects.mycar.mycar_server.entities.Usuario;
import com.projects.mycar.mycar_server.enums.RolUsuario;
import com.projects.mycar.mycar_server.repositories.UsuarioRepository;
import com.projects.mycar.mycar_server.services.UsuarioService;
import com.projects.mycar.mycar_server.services.mapper.UsuarioMapper;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario, UsuarioDTO, Long> implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper mapperUsuario;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              UsuarioMapper usuarioMapper,
                              PasswordEncoder passwordEncoder) {
        super(usuarioRepository, usuarioMapper);
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapperUsuario = usuarioMapper;
    }

    @Override
    @Transactional
    public UsuarioDTO crearUsuario(String nombre, String clave, RolUsuario rol) {
        if (usuarioRepository.findByNombreUsuario(nombre).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombreUsuario(nombre);
        nuevo.setClave(passwordEncoder.encode(clave));
        nuevo.setRol(rol);
        nuevo.setActivo(true);

        Usuario usuarioGuardado = usuarioRepository.save(nuevo);
        return mapperUsuario.toDto(usuarioGuardado);
    }


    @Override
    @Transactional
    public void modificarUsuario(Long id, String nombre, String clave, RolUsuario rol) {
        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombreUsuario(nombre);
        usuario.setClave(passwordEncoder.encode(clave));
        usuario.setRol(rol);

        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void validar(String nombreUsuario, String clave, RolUsuario rol) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(clave, usuario.getClave())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        if (!usuario.getRol().equals(rol)) {
            throw new RuntimeException("El rol no coincide");
        }
    }


    @Override
    @Transactional
    public UsuarioDTO buscarUsuarioPorNombre(String nombre) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombre)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return baseMapper.toDto(usuario);
    }

    @Override
    @Transactional
    public void modificarClave(Long id, String claveActual, String claveNueva, String confirmarClave) {
        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(claveActual, usuario.getClave())) {
            throw new RuntimeException("La clave actual no es correcta");
        }

        if (!claveNueva.equals(confirmarClave)) {
            throw new RuntimeException("Las claves nuevas no coinciden");
        }

        usuario.setClave(passwordEncoder.encode(claveNueva));
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public UsuarioDTO login(String nombreUsuario, String clave) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(clave, usuario.getClave())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return baseMapper.toDto(usuario);
    }

    // ---------------- MÉTODOS ABSTRACTOS DE BaseServiceImpl ----------------

    @Override
    protected Usuario updateEntityFromDto(Usuario entity, UsuarioDTO dto) throws Exception {
        entity.setNombreUsuario(dto.getNombreUsuario());
        entity.setRol(dto.getRol());
        // solo actualiza clave si viene informada
        if (dto.getClave() != null && !dto.getClave().isEmpty()) {
            entity.setClave(passwordEncoder.encode(dto.getClave()));
        }
        return entity;
    }

    @Override
    protected void validate(UsuarioDTO dto) throws Exception {
        if (dto.getNombreUsuario() == null || dto.getNombreUsuario().isEmpty()) {
            throw new Exception("El nombre de usuario es obligatorio");
        }
    }

    @Override
    protected void beforeSave(UsuarioDTO dto) throws Exception {
        if (usuarioRepository.findByNombreUsuario(dto.getNombreUsuario()).isPresent()) {
            throw new Exception("El nombre de usuario ya está registrado");
        }
    }

    @Override
    protected void afterSave(Usuario entity) throws Exception {

    }

    @Override
    protected void afterUpdate(Usuario entity) throws Exception {

    }

    @Override
    protected void beforeDelete(Usuario entity) throws Exception {

    }

    @Override
    protected void afterDelete(Usuario entity) throws Exception {

    }

    @Override
    public List<UsuarioDTO> findAllByIds(Iterable<Long> ids) throws Exception {
        return null;
    }
}

