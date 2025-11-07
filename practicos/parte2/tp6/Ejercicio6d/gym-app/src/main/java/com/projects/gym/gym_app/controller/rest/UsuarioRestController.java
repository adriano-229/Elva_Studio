package com.projects.gym.gym_app.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.gym.gym_app.controller.rest.dto.UpdatePasswordRequest;
import com.projects.gym.gym_app.controller.rest.dto.UsuarioProfileDTO;
import com.projects.gym.gym_app.domain.Usuario;
import com.projects.gym.gym_app.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioRestController(UsuarioRepository usuarioRepository,
                                 PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioProfileDTO> me(Authentication authentication) {
        Usuario usuario = findCurrentUser(authentication);
        UsuarioProfileDTO dto = new UsuarioProfileDTO(
                usuario.getId(),
                usuario.getNombreUsuario(),
                usuario.getRol().name()
        );
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/me/password")
    @Transactional
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UpdatePasswordRequest request,
                                               Authentication authentication) {
        Usuario usuario = findCurrentUser(authentication);
        if (!passwordEncoder.matches(request.currentPassword(), usuario.getClave())) {
            throw new IllegalArgumentException("La clave actual no es válida");
        }
        usuario.setClave(passwordEncoder.encode(request.newPassword()));
        return ResponseEntity.noContent().build();
    }

    private Usuario findCurrentUser(Authentication authentication) {
        String username;
        if (authentication.getPrincipal() instanceof UserDetails details) {
            username = details.getUsername();
        } else {
            username = authentication.getName();
        }

        return usuarioRepository.findByNombreUsuarioAndEliminadoFalse(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }
}
