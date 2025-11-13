package com.projects.mycar.mycar_server.controller;

import com.projects.mycar.mycar_server.dto.CostoVehiculoDTO;
import com.projects.mycar.mycar_server.entities.CostoVehiculo;
import com.projects.mycar.mycar_server.services.impl.CostoVehiculoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/costoVehiculo")
public class CostoVehiculoController extends BaseControllerImpl<CostoVehiculo, CostoVehiculoDTO, CostoVehiculoServiceImpl> {


}
