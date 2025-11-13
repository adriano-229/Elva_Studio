package com.projects.gym.gym_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.projects.gym.gym_app.domain.enums.Mes;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sucursal")
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 150)
    private String nombre;
    
    public static final Mes mesCreacion = Mes.ENERO;
	public static final Long anioCreacion = (long) 2025;

    @Column(nullable = false)
    private boolean eliminado = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;
    
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="direccion_id", nullable=false)
    private Direccion direccion;
}
