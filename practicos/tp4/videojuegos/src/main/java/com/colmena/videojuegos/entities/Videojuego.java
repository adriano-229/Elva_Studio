package com.colmena.videojuegos.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "videojuegos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Videojuego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "{NotEmpty.Videojuego.titulo}")
    private String titulo;
    
    @Size(min=5, max=100, message = "La descripcion debe ser entre 5 y 100 caracteres")
    private String descripcion;
    
    
    private String imagen;
    
    @Min(value = 5, message = "El precio ser minimo 5")
    @Max(value = 1000, message= "El precio debe ser menor a 1000")
    private float precio;
    
    @Min(value = 1, message = "El stock ser minimo 1")
    @Max(value = 1000, message= "El stock debe ser menor a 1000")
    private short stock;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message="Debe ser igual o menor a la fecha de hoy")
    @NotNull(message="La fecha no puede ser nula")
    private Date fechaLanzamiento;
    private boolean activo = true;
    
    @NotNull(message="Es requerido el estudio")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_estudio", nullable = false)
    private Estudio estudio;

    @NotNull(message="Es requerida la categoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_categoria", nullable = false)
    private Categoria categoria;
}