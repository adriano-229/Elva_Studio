package edu.egg.tinder.config;

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
            .requestMatchers("/", "/registro", "/css/**", "/img/**", "/scss/**", "/vendor/**").permitAll()  // rutas públicas
            .anyRequest().authenticated()  // el resto requiere login
        )
        .formLogin(form -> form
            .loginPage("/login")  // tu propia página de login (opcional)
            .permitAll()
        )
        .logout(logout -> logout.permitAll());

        return http.build();
    }
}
