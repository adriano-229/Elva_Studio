package com.example.mycar.controller;

import com.example.mycar.dto.CodigoDescuentoDTO;
import com.example.mycar.entities.CodigoDescuento;
import com.example.mycar.services.impl.CodigoDescuentoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/codigoDescuento")
public class CodigoDescuentoController extends BaseControllerImpl<CodigoDescuento, CodigoDescuentoDTO, CodigoDescuentoServiceImpl> {

    @GetMapping("/searchByCodigo")
    public ResponseEntity<?> searchByCodigo(@RequestParam String codigo) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.findByCodigo(codigo));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @GetMapping("/existsByCodigo")
    public ResponseEntity<?> existsByCodigo(@RequestParam String codigo) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.existsByCodigo(codigo));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }
}
