package com.elva.tp1.config;

import com.elva.tp1.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final boolean ENABLE_SECURITY = true;

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        if (ENABLE_SECURITY) {
            http
                    .userDetailsService(userDetailsService)
                    .authorizeHttpRequests(authz -> authz
                            // Recursos públicos
                            .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                            // Solo ADMIN puede acceder a usuarios y gestión de direcciones
                            .requestMatchers("/usuarios/**").hasRole("ADMIN")
                            .requestMatchers("/paises/**", "/provincias/**", "/departamentos/**").hasRole("ADMIN")

                            // Solo ADMIN puede crear/eliminar empresas y proveedores
                            .requestMatchers("/empresas/nueva", "/empresas/eliminar/**").hasRole("ADMIN")
                            .requestMatchers("/proveedores/nuevo", "/proveedores/eliminar/**").hasRole("ADMIN")

                            // USUARIO y ADMIN pueden ver y editar empresas y proveedores
                            .requestMatchers("/empresas/**", "/proveedores/**").hasAnyRole("ADMIN", "USUARIO")

                            // Cambio de clave personal (solo su propia clave)
                            .requestMatchers("/cambiar-clave").authenticated()

                            // Página principal accesible para usuarios autenticados
                            .requestMatchers("/", "/home").authenticated()

                            // La totalidad de lo demás requiere autenticación
                            .anyRequest().authenticated()
                    )
                    .formLogin(form -> form
                            .loginPage("/login")
                            .defaultSuccessUrl("/", true)
                            .failureUrl("/login?error=true")
                            .permitAll()
                    )
                    .logout(logout -> logout
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/login?logout=true")
                            .permitAll()
                    )
                    .csrf(AbstractHttpConfigurer::disable); // Para simplificar el desarrollo

        } else {
            http
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                    .csrf(AbstractHttpConfigurer::disable)
                    .addFilterBefore((request, response, chain) -> {
                        User admin = new User("admin", "", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(admin, null, admin.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        chain.doFilter(request, response);
                    }, AbstractPreAuthenticatedProcessingFilter.class);
        }
        return http.build();

    }
}
