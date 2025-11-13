package com.projects.mycar.mycar_server.controller;

import com.projects.mycar.mycar_server.dto.ClienteDTO;
import com.projects.mycar.mycar_server.entities.Cliente;
import com.projects.mycar.mycar_server.services.impl.ClienteServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cliente")
public class ClienteController extends BaseControllerImpl<Cliente, ClienteDTO, ClienteServiceImpl> {

}
