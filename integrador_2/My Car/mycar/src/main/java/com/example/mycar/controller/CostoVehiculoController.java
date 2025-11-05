package com.example.mycar.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mycar.dto.CostoVehiculoDTO;
import com.example.mycar.entities.CostoVehiculo;
import com.example.mycar.services.impl.CostoVehiculoServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/costoVehiculo")
public class CostoVehiculoController extends BaseControllerImpl<CostoVehiculo, CostoVehiculoDTO, CostoVehiculoServiceImpl>{
	

}
