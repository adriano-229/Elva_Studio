package com.example.biblioteca.iterator;

import java.util.List;

import com.example.biblioteca.domain.dto.LibroDTO;

public class AutorLibroIterator implements LibroIterator {

    private final List<LibroDTO> libros;
    private final String nombreAutor;
    private int posicion = 0;

    public AutorLibroIterator(List<LibroDTO> libros, String nombreAutor) {
        this.libros = libros;
        this.nombreAutor = nombreAutor.toLowerCase(); 
    }

    @Override
    public boolean hasNext() {
        while (posicion < libros.size()) {
            LibroDTO libro = libros.get(posicion);
            boolean encontrado = libro.getAutores().stream()
                .anyMatch(a -> a.getNombre().toLowerCase().contains(nombreAutor));
            if (encontrado) {
                return true;
            }
            posicion++;
        }
        return false;
    }

    @Override
    public LibroDTO next() {
        if (!hasNext()) {
            return null;
        }
        return libros.get(posicion++);
    }
}
