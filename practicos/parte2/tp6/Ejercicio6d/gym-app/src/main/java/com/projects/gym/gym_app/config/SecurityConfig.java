package com.projects.gym.gym_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.projects.gym.gym_app.security.RoleBasedAuthenticationSuccessHandler;
import com.projects.gym.gym_app.security.jwt.JwtAccessDeniedHandler;
import com.projects.gym.gym_app.security.jwt.JwtAuthenticationEntryPoint;
import com.projects.gym.gym_app.security.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final RequestMatcher REST_API_MATCHER = new OrRequestMatcher(
            new AntPathRequestMatcher("/api/auth/**"),
            new AntPathRequestMatcher("/api/usuarios/**"),
            new AntPathRequestMatcher("/api/mensajes/**"),
            new AntPathRequestMatcher("/api/admin/socios/**"),
            new AntPathRequestMatcher("/api/admin/empleados/**"),
            new AntPathRequestMatcher("/api/admin/sucursales/**"),
            new AntPathRequestMatcher("/api/admin/empresas/**"),
            new AntPathRequestMatcher("/api/admin/valor-cuota/**"),
            new AntPathRequestMatcher("/api/admin/facturas/**"),
            new AntPathRequestMatcher("/api/admin/rutinas/**")
    );

    private final RoleBasedAuthenticationSuccessHandler successHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher(REST_API_MATCHER)
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(handling -> handling
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(jwtAccessDeniedHandler)
            )
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/auth/login").permitAll()
                    .requestMatchers("/api/usuarios/**").authenticated()
                    .requestMatchers("/api/mensajes/**").hasAnyRole("ADMIN", "OPERADOR")
                    .requestMatchers("/api/admin/socios/**").hasRole("ADMIN")
                    .requestMatchers("/api/admin/empleados/**").hasRole("ADMIN")
                    .requestMatchers("/api/admin/sucursales/**").hasRole("ADMIN")
                    .requestMatchers("/api/admin/empresas/**").hasRole("ADMIN")
                    .requestMatchers("/api/admin/valor-cuota/**").hasAnyRole("ADMIN", "OPERADOR")
                    .requestMatchers("/api/admin/facturas/**").hasAnyRole("ADMIN", "OPERADOR")
                    .requestMatchers("/api/admin/rutinas/**").hasAnyRole("ADMIN", "PROFESOR")
                    .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/error", "/actuator/health").permitAll()
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
                .requestMatchers("/h2-console/**").permitAll()
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
	            .ignoringRequestMatchers("/pagos/webhook", "/h2-console/**")
	        )
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
