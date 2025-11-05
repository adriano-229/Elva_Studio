package com.example.mycar.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mycar.dto.ImagenDTO;
import com.example.mycar.entities.Imagen;
import com.example.mycar.services.impl.ImagenServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/imagen")
public class ImagenController extends BaseControllerImpl<Imagen, ImagenDTO, ImagenServiceImpl>{

}
