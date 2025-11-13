package com.example.mycar.controller;

import com.example.mycar.dto.ClienteDTO;
import com.example.mycar.entities.Cliente;
import com.example.mycar.services.impl.ClienteServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cliente")
public class ClienteController extends BaseControllerImpl<Cliente, ClienteDTO, ClienteServiceImpl> {

}
