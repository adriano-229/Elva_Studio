package com.example.mycar.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mycar.dto.PaisDTO;
import com.example.mycar.entities.Pais;
import com.example.mycar.services.impl.PaisServiceImpl;

@RestController
@RequestMapping("/api/v1/paises")
public class PaisController extends BaseControllerImpl<Pais, PaisDTO, PaisServiceImpl>{

}
