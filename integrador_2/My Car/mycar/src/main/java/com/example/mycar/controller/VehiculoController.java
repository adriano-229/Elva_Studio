package com.example.mycar.controller;

import com.example.mycar.dto.VehiculoDTO;
import com.example.mycar.entities.Vehiculo;
import com.example.mycar.enums.EstadoVehiculo;
import com.example.mycar.services.impl.VehiculoServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/vehiculos")
public class VehiculoController extends BaseControllerImpl<Vehiculo, VehiculoDTO, VehiculoServiceImpl> {

	
    @GetMapping("/searchByEstadoYPeriodo")
    public ResponseEntity<?> searchByEstadoYPeriodo(@RequestParam EstadoVehiculo estado,
                                                    @RequestParam LocalDate desde,
                                                    @RequestParam LocalDate hasta) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.buscarVehiculosPorEstadoYPeriodo(estado, desde, hasta));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @GetMapping("/searchByEstado")
    public ResponseEntity<?> searchByEstado(@RequestParam EstadoVehiculo estado) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.buscarPorEstado(estado));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }
    
    @GetMapping("/searchByPatente")
    public ResponseEntity<?> searchByPatente(@RequestParam String patente){
    	try {
    		return ResponseEntity.status(HttpStatus.OK).body(servicio.buscarPorPatente(patente));
    	} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }
    
    
    

}
