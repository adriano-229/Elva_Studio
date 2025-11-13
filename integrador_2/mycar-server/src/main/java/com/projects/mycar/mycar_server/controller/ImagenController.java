package com.projects.mycar.mycar_server.controller;

import com.projects.mycar.mycar_server.dto.ImagenDTO;
import com.projects.mycar.mycar_server.entities.Imagen;
import com.projects.mycar.mycar_server.services.impl.ImagenServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/imagen")
public class ImagenController extends BaseControllerImpl<Imagen, ImagenDTO, ImagenServiceImpl> {

}
