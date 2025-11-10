package com.example.mycar.controller;

import com.example.mycar.dto.AlquilerDTO;
import com.example.mycar.entities.Alquiler;
import com.example.mycar.services.impl.AlquilerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/alquileres")
public class AlquilerController extends BaseControllerImpl<Alquiler, AlquilerDTO, AlquilerServiceImpl> {

    @GetMapping("/searchByPeriodo")
    public ResponseEntity<?> searchByPeriodo(@RequestParam LocalDate desde, @RequestParam LocalDate hasta) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.searchPorPeriodo(desde, hasta));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @GetMapping("/searchByCliente")
    public ResponseEntity<?> searchByCliente(@RequestParam Long clienteId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.searchByCliente(clienteId));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }
}
