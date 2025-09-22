package com.projects.gym.gym_app.config;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute("usuario")
    public String usuario(Authentication authentication) {
        return authentication != null ? authentication.getName() : null;
    }

    @ModelAttribute("esAdmin")
    public boolean esAdmin(Authentication authentication) {
        return hasRole(authentication, "ROLE_ADMIN");
    }

    @ModelAttribute("esSocio")
    public boolean esSocio(Authentication authentication) {
        return hasRole(authentication, "ROLE_SOCIO");
    }

    @ModelAttribute("esOperador")
    public boolean esOperador(Authentication authentication) {
        return hasRole(authentication, "ROLE_OPERADOR");
    }

    @ModelAttribute("esStaff")
    public boolean esStaff(Authentication authentication) {
        return hasRole(authentication, "ROLE_ADMIN") || hasRole(authentication, "ROLE_OPERADOR");
    }
    private boolean hasRole(Authentication authentication, String authority) {
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> authority.equals(a.getAuthority()));
    }

}
