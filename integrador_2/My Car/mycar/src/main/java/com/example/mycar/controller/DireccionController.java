package com.example.mycar.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mycar.dto.DireccionDTO;
import com.example.mycar.entities.Direccion;
import com.example.mycar.services.impl.DireccionServiceImpl;

@RestController
@RequestMapping("/api/v1/direcciones")
public class DireccionController extends BaseControllerImpl<Direccion, DireccionDTO, DireccionServiceImpl>{
	
	@GetMapping("/getCompleta")
    public ResponseEntity<?> getDireccionCompleta(@RequestParam DireccionDTO dir) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.getDireccionCompleta(dir));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }
	
}
