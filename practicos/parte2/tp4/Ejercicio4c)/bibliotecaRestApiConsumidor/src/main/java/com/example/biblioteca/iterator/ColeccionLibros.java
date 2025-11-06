package com.example.biblioteca.iterator;

import java.util.List;

import com.example.biblioteca.domain.dto.LibroDTO;

public class ColeccionLibros {

	private final List<LibroDTO> libros;

    public ColeccionLibros(List<LibroDTO> libros) {
        this.libros = libros;
    }

    public LibroIterator getIterator(String nombreAutor) {
        return new AutorLibroIterator(libros, nombreAutor);
    }
}
