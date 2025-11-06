package com.example.contactosApp.config;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute("usuario")
    public String usuario(Authentication authentication) {
        return authentication != null ? authentication.getName() : null;
    }
    
    @ModelAttribute("esUser")
    public boolean esUser(Authentication authentication) {
        return hasRole(authentication, "ROLE_USER");
    }

    @ModelAttribute("esAdmin")
    public boolean esAdmin(Authentication authentication) {
        return hasRole(authentication, "ROLE_ADMIN");
    }

    // Otros roles similares...
    
    private boolean hasRole(Authentication authentication, String authority) {
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> authority.equals(a.getAuthority()));
    }
    
    

}
