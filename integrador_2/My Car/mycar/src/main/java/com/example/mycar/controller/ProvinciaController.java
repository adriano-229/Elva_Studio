package com.example.mycar.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mycar.dto.ProvinciaDTO;
import com.example.mycar.entities.Provincia;
import com.example.mycar.services.impl.ProvinciaServiceImpl;

@RestController
@RequestMapping("/api/v1/provincias")
public class ProvinciaController extends BaseControllerImpl<Provincia, ProvinciaDTO, ProvinciaServiceImpl>{

}
