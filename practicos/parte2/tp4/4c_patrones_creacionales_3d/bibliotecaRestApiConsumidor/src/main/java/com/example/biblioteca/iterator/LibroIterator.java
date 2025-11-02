package com.example.biblioteca.iterator;

import com.example.biblioteca.domain.dto.LibroDTO;

public interface LibroIterator {
	
	boolean hasNext();
    LibroDTO next();

}
