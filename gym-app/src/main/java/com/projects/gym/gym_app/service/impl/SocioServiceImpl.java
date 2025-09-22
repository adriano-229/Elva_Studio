package com.projects.gym.gym_app.service.impl;

import com.projects.gym.gym_app.domain.*;
import com.projects.gym.gym_app.domain.enums.Rol;
import com.projects.gym.gym_app.repository.*;
import com.projects.gym.gym_app.service.SocioService;
import com.projects.gym.gym_app.service.dto.SocioFormDTO;
import com.projects.gym.gym_app.service.mapper.SocioMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder; // opcional
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SocioServiceImpl implements SocioService {

    private final SocioRepository socioRepo;
    private final LocalidadRepository localidadRepo;
    private final DireccionRepository direccionRepo;
    private final UsuarioRepository usuarioRepo; // si no es cascada, usar repo
    private final SucursalRepository sucursalRepo;
    private final PasswordEncoder passwordEncoder; // si usás Spring Security (opcional)

    @Override
    public SocioFormDTO crear(SocioFormDTO d) {
        validar(d);

        // Localidad requerida
        Localidad loc = localidadRepo.findById(d.getLocalidadId())
            .orElseThrow(() -> new EntityNotFoundException("Localidad no encontrada"));

        // Direccion: nueva si no mandan 'direccionId'
        Direccion dir;
        if (d.getDireccionId() != null && !d.getDireccionId().isBlank()) {
            dir = direccionRepo.findById(d.getDireccionId())
                    .orElseThrow(() -> new EntityNotFoundException("Dirección no encontrada"));
        } else {
            dir = new Direccion();
            SocioMapper.fillDireccionFromDto(dir, d, loc);
            dir = direccionRepo.save(dir);
        }

        Sucursal sucursal = sucursalRepo.findById(d.getSucursalId())
                .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada"));

        // Usuario (composición)
        Usuario u = new Usuario();
        SocioMapper.fillUsuarioFromDto(u, d);
        if (passwordEncoder != null) {
            u.setClave(passwordEncoder.encode(d.getClave()));
        }

        // Socio
        Socio s = new Socio();
        SocioMapper.fillPersonaFromDto(s, d);
        s.setNumeroSocio(d.getNumeroSocio());
        s.setEliminado(false);
        s.setDireccion(dir);
        s.setUsuario(u); // composición
        s.setSucursal(sucursal);

        s = socioRepo.save(s); // con cascade a usuario si lo configuraste en la entidad
        return SocioMapper.toDto(s);
    }

    @Override @Transactional(readOnly = true)
    public SocioFormDTO buscarPorId(String id) {
        Long socioId = parseSocioId(id);
        return socioRepo.findById(socioId).map(SocioMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Socio no encontrado"));
    }

    @Override
    public SocioFormDTO modificar(String id, SocioFormDTO d) {
        Socio s = socioRepo.findById(parseSocioId(id))
                .orElseThrow(() -> new EntityNotFoundException("Socio no encontrado"));

        validar(d);

        // actualizar persona
        SocioMapper.fillPersonaFromDto(s, d);
        s.setNumeroSocio(d.getNumeroSocio());

        // actualizar direccion (si viene id, reasignar; si no, actualizar actual)
        Localidad loc = localidadRepo.findById(d.getLocalidadId())
                .orElseThrow(() -> new EntityNotFoundException("Localidad no encontrada"));

        if (d.getDireccionId()!=null && !d.getDireccionId().isBlank()) {
            Direccion nueva = direccionRepo.findById(d.getDireccionId())
                    .orElseThrow(() -> new EntityNotFoundException("Dirección no encontrada"));
            s.setDireccion(nueva);
        } else {
            Direccion actual = s.getDireccion();
            if (actual == null) {
                actual = new Direccion();
            }
            SocioMapper.fillDireccionFromDto(actual, d, loc);
            s.setDireccion(direccionRepo.save(actual));
        }

        Sucursal sucursal = sucursalRepo.findById(d.getSucursalId())
                .orElseThrow(() -> new EntityNotFoundException("Sucursal no encontrada"));

        // actualizar usuario (composición)
        Usuario u = s.getUsuario();
        if (u == null) u = new Usuario();
        u.setNombreUsuario(d.getNombreUsuario());
        if (passwordEncoder != null && d.getClave()!=null && !d.getClave().isBlank()) {
            u.setClave(passwordEncoder.encode(d.getClave()));
        }
        u.setRol(Rol.valueOf(d.getRol()));
        u.setEliminado(false);
        s.setUsuario(u);
        s.setSucursal(sucursal);

        return SocioMapper.toDto(s);
    }

    @Override
    public void eliminarLogico(String id) {
        Socio s = socioRepo.findById(parseSocioId(id))
                .orElseThrow(() -> new EntityNotFoundException("Socio no encontrado"));
        s.setEliminado(true);
        if (s.getUsuario()!=null) s.getUsuario().setEliminado(true); // por política
    }

    @Override @Transactional(readOnly = true)
    public Page<SocioFormDTO> listar(String filtro, Pageable pageable) {
        Page<Socio> page = (filtro==null || filtro.isBlank())
                ? socioRepo.findByEliminadoFalse(pageable)
                : socioRepo.searchActivos("%"+filtro.trim().toLowerCase()+"%", pageable);
        return page.map(SocioMapper::toDto);
    }

    private void validar(SocioFormDTO d) {
        // ejemplos mínimos
        if (d.getNumeroSocio() <= 0) throw new IllegalArgumentException("Número de socio inválido");
        if (d.getSucursalId() == null || d.getSucursalId().isBlank()) throw new IllegalArgumentException("Sucursal no indicada");
    }

    private Long parseSocioId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id de socio inválido");
        }
        try {
            return Long.valueOf(id);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Id de socio inválido", ex);
        }
    }
}
