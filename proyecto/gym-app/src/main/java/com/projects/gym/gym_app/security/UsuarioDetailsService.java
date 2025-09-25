package com.projects.gym.gym_app.security;

import com.projects.gym.gym_app.domain.Usuario;
import com.projects.gym.gym_app.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombreUsuarioAndEliminadoFalse(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.withUsername(usuario.getNombreUsuario())
                .password(usuario.getClave())
                .roles(usuario.getRol().name())
                .disabled(usuario.isEliminado())
                .build();
    }
}
