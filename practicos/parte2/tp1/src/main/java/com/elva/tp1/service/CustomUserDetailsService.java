package com.elva.tp1.service;

import com.elva.tp1.domain.Usuario;
import com.elva.tp1.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String cuenta) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCuenta(cuenta)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + cuenta));

        return new org.springframework.security.core.userdetails.User(
                usuario.getCuenta(),
                usuario.getClave(),
                usuario.isEliminado(),
                true, // account non expired
                true, // credentials non expired
                true, // account non-locked
                getAuthorities(usuario)
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Usuario usuario) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()));
        return authorities;
    }
}
