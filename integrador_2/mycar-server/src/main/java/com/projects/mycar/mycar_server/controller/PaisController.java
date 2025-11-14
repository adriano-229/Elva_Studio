package com.projects.mycar.mycar_server.controller;

import com.projects.mycar.mycar_server.dto.PaisDTO;
import com.projects.mycar.mycar_server.entities.Pais;
import com.projects.mycar.mycar_server.services.impl.PaisServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/paises")
public class PaisController extends BaseControllerImpl<Pais, PaisDTO, PaisServiceImpl> {

}
