package com.example.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()   // permite todo sin login
            )
            .csrf(csrf -> csrf.disable())  // desactiva CSRF para formularios simples
            .formLogin(login -> login.disable()) // desactiva login por formulario
            .httpBasic(basic -> basic.disable()); // desactiva login básico

        return http.build();
    }
}
