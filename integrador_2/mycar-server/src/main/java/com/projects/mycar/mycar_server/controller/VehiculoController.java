package com.projects.mycar.mycar_server.controller;

import com.projects.mycar.mycar_server.dto.VehiculoDTO;
import com.projects.mycar.mycar_server.entities.Vehiculo;
import com.projects.mycar.mycar_server.enums.EstadoVehiculo;
import com.projects.mycar.mycar_server.services.impl.VehiculoServiceImpl;
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

}
