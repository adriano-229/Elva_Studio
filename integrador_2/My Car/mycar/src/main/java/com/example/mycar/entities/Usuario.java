package com.example.mycar.entities;

import com.example.mycar.enums.RolUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario extends Base {
	
	@Column(name = "nombre_usuario", nullable = false, unique = true, length = 50)
    private String nombreUsuario;
	
    @Column(name = "clave",nullable = false)
    private String clave;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "rol",nullable = false)
    private RolUsuario rol;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id")
    private Persona persona;
}
