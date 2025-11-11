package com.example.mycar.controller;

import com.example.mycar.dto.ImagenDTO;
import com.example.mycar.entities.Imagen;
import com.example.mycar.services.impl.ImagenServiceImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/imagen")
public class ImagenController extends BaseControllerImpl<Imagen, ImagenDTO, ImagenServiceImpl> {
	
	@PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/binario/{id}")
    public ResponseEntity<byte[]> getImagen(@PathVariable Long id) {
        Imagen imagen = entityManager.find(Imagen.class, id);
        if (imagen == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, imagen.getMime())
                .body(imagen.getContenido());
    }
}

