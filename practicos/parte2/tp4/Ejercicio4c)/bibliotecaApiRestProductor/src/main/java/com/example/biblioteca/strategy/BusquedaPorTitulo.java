package com.example.biblioteca.strategy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.entities.Libro;
import com.example.biblioteca.repository.LibroRepository;


@Service("busquedaPorTitulo")
public class BusquedaPorTitulo implements BusquedaLibroStrategy {
	
	@Autowired
    private LibroRepository libroRepository;

    @Override
    public List<Libro> buscar(String valor) {
        return libroRepository.findByTituloContainingIgnoreCaseAndActivoTrue(valor);
    }
}

