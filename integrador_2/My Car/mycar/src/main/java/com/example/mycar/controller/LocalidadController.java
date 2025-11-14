package com.example.mycar.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mycar.dto.LocalidadDTO;
import com.example.mycar.entities.Localidad;
import com.example.mycar.services.impl.LocalidadServiceImpl;

@RestController
@RequestMapping("/api/v1/localidades")
public class LocalidadController extends BaseControllerImpl<Localidad, LocalidadDTO, LocalidadServiceImpl>{

}
