package com.example.contactosApp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.contactosApp.domain.Persona;
import com.example.contactosApp.domain.Usuario;
import com.example.contactosApp.domain.enums.Rol;
import com.example.contactosApp.repository.PersonaRepository;
import com.example.contactosApp.repository.UsuarioRepository;

@SpringBootApplication
public class ContactosAppApplication {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UsuarioRepository userRepository;
	
	@Autowired
	PersonaRepository personaRepository;

	public static void main(String[] args) {
		SpringApplication.run(ContactosAppApplication.class, args);
	}
	
	/*@Bean
	CommandLineRunner init() {
		return args -> {
			
			Usuario usuario = Usuario.builder()
					.cuenta("admin")
					.clave(passwordEncoder.encode("admin123"))
					.rol(Rol.ADMIN)
					.build();
			
			Persona persona = Persona.builder()
					.nombre("Jose")
					.apellido("Perez")
					.build();
			
			personaRepository.save(persona);
			usuario.setPersona(persona);
			userRepository.save(usuario);
			
			Usuario usuario1 = Usuario.builder()
					.cuenta("juan")
					.clave(passwordEncoder.encode("juan123"))
					.rol(Rol.USER)
					.build();
			
			usuario1.setPersona(persona);
			
			Usuario usuario2 = Usuario.builder()
					.cuenta("maria")
					.clave(passwordEncoder.encode("maria123"))
					.rol(Rol.USER)
					.build();
			
			Usuario usuario3 = Usuario.builder()
					.cuenta("carlos")
					.clave(passwordEncoder.encode("carlos123"))
					.rol(Rol.USER)
					.build();
			
			Usuario usuario4 = Usuario.builder()
					.cuenta("ana")
					.clave(passwordEncoder.encode("ana123"))
					.rol(Rol.USER)
					.build();
			
			Usuario usuario5 = Usuario.builder()
					.cuenta("luis")
					.clave(passwordEncoder.encode("luis123"))
					.rol(Rol.USER)
					.build();
			
			
			
			userRepository.save(usuario1);
			userRepository.save(usuario2);
			userRepository.save(usuario3);
			userRepository.save(usuario4);
			userRepository.save(usuario5);
			
		};
	}*/
}
