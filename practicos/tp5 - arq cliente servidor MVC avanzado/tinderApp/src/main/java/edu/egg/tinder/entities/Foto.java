package edu.egg.tinder.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "foto")
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mime;
    private String nombre;

    //indica que el tipo de dato sera pesado
    @Lob
    @Basic(fetch = FetchType.LAZY) // solo carga el contenido cuando se le pide
    private byte[] contenido;

}
