package com.example.mycar.controller;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mycar.dto.VehiculoDTO;
import com.example.mycar.entities.Vehiculo;
import com.example.mycar.enums.EstadoVehiculo;
import com.example.mycar.services.impl.VehiculoServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/vehiculos")
public class VehiculoController extends BaseControllerImpl<Vehiculo, VehiculoDTO, VehiculoServiceImpl>{
	
	@GetMapping("/searchByEstadoYPeriodo")
	public ResponseEntity<?> searchByEstadoYPeriodo(@RequestParam EstadoVehiculo estado,
													@RequestParam LocalDate desde,
													@RequestParam LocalDate hasta){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servicio.buscarVehiculosPorEstadoYPeriodo(estado, desde, hasta));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
	}
	
	@GetMapping("/searchByEstado")
	public ResponseEntity<?> searchByEstado(@RequestParam EstadoVehiculo estado){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servicio.buscarPorEstado(estado));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
	}
	
}
