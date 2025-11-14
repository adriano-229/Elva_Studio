package com.example.mycar.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mycar.dto.DepartamentoDTO;
import com.example.mycar.entities.Departamento;
import com.example.mycar.services.impl.DepartamentoServiceImpl;

@RestController
@RequestMapping("/api/v1/departamentos")
public class DepartamentoController extends BaseControllerImpl<Departamento, DepartamentoDTO, DepartamentoServiceImpl>{

}
