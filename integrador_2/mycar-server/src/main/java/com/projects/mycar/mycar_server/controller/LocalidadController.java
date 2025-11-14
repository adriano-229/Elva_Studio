package com.projects.mycar.mycar_server.controller;

import com.projects.mycar.mycar_server.dto.LocalidadDTO;
import com.projects.mycar.mycar_server.entities.Localidad;
import com.projects.mycar.mycar_server.services.impl.LocalidadServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/localidades")
public class LocalidadController extends BaseControllerImpl<Localidad, LocalidadDTO, LocalidadServiceImpl> {

}
