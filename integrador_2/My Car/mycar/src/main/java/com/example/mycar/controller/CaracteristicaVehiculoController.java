package com.example.mycar.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mycar.dto.CaracteristicaVehiculoDTO;
import com.example.mycar.entities.CaracteristicaVehiculo;
import com.example.mycar.services.impl.CaracteristicaVehiculoServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/caracteristicaVehiculo")
public class CaracteristicaVehiculoController extends BaseControllerImpl<CaracteristicaVehiculo, CaracteristicaVehiculoDTO, CaracteristicaVehiculoServiceImpl>{

	/*@GetMapping("searchDisponible")
	public ResponseEntity<?> searchByEstadoVehiculo(@RequestParam EstadoVehiculo estado) {
	    try {
	        
	        return ResponseEntity.status(HttpStatus.OK).body(servicio.findByEstadoVehiculo(estado));

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	            .body("{\"error\":\"Error. Por favor intente más tarde.\"}");
	    }
	}*/
	
}
