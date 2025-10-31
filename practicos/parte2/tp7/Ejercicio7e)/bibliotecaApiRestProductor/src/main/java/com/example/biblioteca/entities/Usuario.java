package com.example.biblioteca.entities;

import com.example.biblioteca.enums.Rol;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends Base {
	
	private String nombreUsuario;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Rol rol;
}
