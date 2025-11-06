package com.example.biblioteca.adapter;

import org.springframework.stereotype.Component;

import com.example.biblioteca.entities.Libro;
import com.example.biblioteca.entities.LibroDigital;
import com.example.biblioteca.entities.LibroFisico;
import com.example.biblioteca.entities.dto.LibroDTO;
import com.example.biblioteca.entities.enums.TipoLibro;

@Component
public class LibroAdapter {

    // De DTO → Entidad
    public Libro toEntity(LibroDTO dto) {
        if (dto == null) return null;

        Libro libro;
        if (dto.getTipo() == TipoLibro.FISICO) {
            libro = new LibroFisico();
            ((LibroFisico) libro).setCantEjemplares(dto.getCantEjemplares());
        } else {
            libro = new LibroDigital();
            ((LibroDigital) libro).setRutaPdf(dto.getRutaPdf());
        }

        libro.setId(dto.getId());
        libro.setActivo(dto.isActivo());
        libro.setFecha(dto.getFecha());
        libro.setGenero(dto.getGenero());
        libro.setPaginas(dto.getPaginas());
        libro.setTitulo(dto.getTitulo());
        libro.setAutores(dto.getAutores());
        return libro;
    }

    // De Entidad → DTO
    public LibroDTO toDTO(Libro libro) {
        if (libro == null) return null;

        LibroDTO dto = new LibroDTO();
        dto.setId(libro.getId());
        dto.setActivo(libro.isActivo());
        dto.setFecha(libro.getFecha());
        dto.setGenero(libro.getGenero());
        dto.setPaginas(libro.getPaginas());
        dto.setTitulo(libro.getTitulo());
        dto.setAutores(libro.getAutores());
        dto.setTipo(libro.getTipo());

        if (libro instanceof LibroFisico fisico) {
            dto.setCantEjemplares(fisico.getCantEjemplares());
        } else if (libro instanceof LibroDigital digital) {
            dto.setRutaPdf(digital.getRutaPdf());
        }

        return dto;
    }
}
