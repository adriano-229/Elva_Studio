package com.projects.gym.gym_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import com.projects.gym.gym_app.security.RoleBasedAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final RoleBasedAuthenticationSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/error").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/admin/usuarios/**", "/admin/config/cuota/**", "/admin/empleados/**").hasRole("ADMIN")
                .requestMatchers("/admin/socios/**").hasAnyRole("ADMIN")
                .requestMatchers("/admin/facturas/**").hasAnyRole("ADMIN", "OPERADOR")
                .requestMatchers("/admin/pagos/**").hasAnyRole("ADMIN", "OPERADOR")
                .requestMatchers("/admin/cuotas/**").hasAnyRole("ADMIN", "OPERADOR")
                .requestMatchers("/admin/mensajes/**").hasAnyRole("ADMIN", "OPERADOR")
                .requestMatchers("/operador/**").hasAnyRole("OPERADOR", "ADMIN")
                .requestMatchers("/profesor/**").hasRole("PROFESOR")
                .requestMatchers("/api/ubicacion/**").hasAnyRole("ADMIN", "OPERADOR")
                .requestMatchers("/api/facturas/**").hasAnyRole("ADMIN", "OPERADOR", "SOCIO")
                .requestMatchers("/socio/**").hasAnyRole("SOCIO", "ADMIN")
                .requestMatchers("/pagos/webhook").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(successHandler)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf
	            .ignoringRequestMatchers("/pagos/webhook") // permite POST sin CSRF
	        );
        
        

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
