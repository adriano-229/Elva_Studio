package com.example.contactosApp.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.contactosApp.domain.Usuario;
import com.example.contactosApp.repository.UsuarioRepository;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleBasedAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_USER = "ROLE_USER";
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        Set<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        if (authorities.contains(ROLE_ADMIN)) {
            return "/home/admin";
        }
        if (authorities.contains(ROLE_USER)) {
            return "/home/user";
        }
        
        return super.determineTargetUrl(request, response, authentication);
    }
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        Usuario usuario = usuarioRepository.findByCuentaAndActivoTrue(username).get();

        ((UsernamePasswordAuthenticationToken) authentication).setDetails(usuario);

        super.onAuthenticationSuccess(request, response, authentication);
    }

}
