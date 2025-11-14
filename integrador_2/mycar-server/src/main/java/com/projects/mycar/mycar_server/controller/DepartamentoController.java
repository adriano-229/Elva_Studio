package com.projects.mycar.mycar_server.controller;

import com.projects.mycar.mycar_server.dto.DepartamentoDTO;
import com.projects.mycar.mycar_server.entities.Departamento;
import com.projects.mycar.mycar_server.services.impl.DepartamentoServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/departamentos")
public class DepartamentoController extends BaseControllerImpl<Departamento, DepartamentoDTO, DepartamentoServiceImpl> {

}
