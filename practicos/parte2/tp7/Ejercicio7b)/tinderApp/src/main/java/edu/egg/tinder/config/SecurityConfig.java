package edu.egg.tinder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import edu.egg.tinder.entities.Usuario;
import edu.egg.tinder.security.jwt.JwtAuthenticationFilter;
import edu.egg.tinder.services.ServicioUsuario;
import jakarta.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler(ServicioUsuario servicioUsuario) {
		return (request, response, authentication) -> {
			Usuario usuario = servicioUsuario.buscarPorMail(authentication.getName());
			HttpSession session = request.getSession(true);
			session.setAttribute("usuarioSession", usuario);
			session.setAttribute("usuario", usuario);
			response.sendRedirect("/inicio");
		};
	}

	@Bean
	@Order(1)
	public SecurityFilterChain apiFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
		http
			.securityMatcher("/api/**")
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/api/auth/**").permitAll()
					.anyRequest().hasRole("USUARIO")
			)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable);

		return http.build();
	}

	@Bean
	@Order(2)
    public SecurityFilterChain webFilterChain(HttpSecurity http, AuthenticationSuccessHandler successHandler) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers("/css/**", "/js/**", "/img/**", "/vendor/**").permitAll()
            		.requestMatchers("/", "/login", "/loginUsuario", "/registro", "/registrar", "/error").permitAll()
            		.anyRequest().authenticated()
            )
            .formLogin(form -> form
            		.loginPage("/login")
            		.loginProcessingUrl("/loginUsuario")
            		.usernameParameter("email")
            		.passwordParameter("clave")
            		.failureUrl("/login?error=true")
            		.successHandler(successHandler)
            		.permitAll()
            )
            .logout(logout -> logout
            		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            		.logoutSuccessUrl("/login?logout=true")
            		.invalidateHttpSession(true)
            		.deleteCookies("JSESSIONID")
            		.permitAll()
            )
            .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
