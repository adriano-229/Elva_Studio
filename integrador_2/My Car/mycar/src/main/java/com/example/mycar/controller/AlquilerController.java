package com.example.mycar.controller;

import com.example.mycar.dto.AlquilerDTO;
import com.example.mycar.entities.Alquiler;
import com.example.mycar.services.impl.AlquilerServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController extends BaseControllerImpl<Alquiler, AlquilerDTO, AlquilerServiceImpl> {
}
