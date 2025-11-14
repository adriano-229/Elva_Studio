package com.projects.mycar.mycar_server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.mycar.mycar_server.dto.ProvinciaDTO;
import com.projects.mycar.mycar_server.entities.Provincia;
import com.projects.mycar.mycar_server.services.impl.ProvinciaServiceImpl;

@RestController
@RequestMapping("/api/v1/provincias")
public class ProvinciaController extends BaseControllerImpl<Provincia, ProvinciaDTO, ProvinciaServiceImpl>{

}
