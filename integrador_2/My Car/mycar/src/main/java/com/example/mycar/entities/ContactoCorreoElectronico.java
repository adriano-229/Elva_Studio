package com.example.mycar.entities;

import com.example.mycar.enums.TipoContacto;
import com.example.mycar.enums.TipoEmpleado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@PrimaryKeyJoinColumn(name = "contacto_id")
@Getter
@Setter
@NoArgsConstructor
public class ContactoCorreoElectronico extends Contacto{
	
	@Column(name = "email", nullable = false)
	private String email;
}
