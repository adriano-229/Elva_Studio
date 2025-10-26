package com.example.biblioteca.iterator;

import java.util.List;
import java.util.NoSuchElementException;

import com.example.biblioteca.domain.dto.LibroDTO;

public class AutorLibroIterator implements LibroIterator {

    private final List<LibroDTO> libros;
    private final String nombreAutor;
    private int posicion = 0;

    public AutorLibroIterator(List<LibroDTO> libros, String nombreAutor) {
        this.libros = libros;
        this.nombreAutor = nombreAutor.toLowerCase();
        moveToNextMatch();  // Inicializamos para que `posicion` apunte al primer match
    }

    private void moveToNextMatch() {
        while (posicion < libros.size()) {
            LibroDTO libro = libros.get(posicion);
            boolean encontrado = libro.getAutores().stream()
                    .anyMatch(a -> {
                        String nombreCompleto = (a.getNombre() + " " + a.getApellido()).toLowerCase();
                        return nombreCompleto.contains(nombreAutor);
                    });
            if (encontrado) {
                break;  // encontramos el siguiente match
            }
            posicion++;
        }
    }

    @Override
    public boolean hasNext() {
        return posicion < libros.size();
    }

    @Override
    public LibroDTO next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        LibroDTO libro = libros.get(posicion);
        posicion++;
        moveToNextMatch();  // avanzamos hasta el próximo match
        return libro;
    }
}

