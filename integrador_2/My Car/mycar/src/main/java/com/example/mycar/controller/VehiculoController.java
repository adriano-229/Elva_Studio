package com.example.mycar.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mycar.dto.VehiculoDTO;
import com.example.mycar.entities.Vehiculo;
import com.example.mycar.services.impl.VehiculoServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/vehiculos")
public class VehiculoController extends BaseControllerImpl<Vehiculo, VehiculoDTO, VehiculoServiceImpl>{

}
