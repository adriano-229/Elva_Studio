package com.projects.mycar.mycar_server.controller;

import com.projects.mycar.mycar_server.dto.FacturaDTO;
import com.projects.mycar.mycar_server.entities.Factura;
import com.projects.mycar.mycar_server.services.impl.FacturaServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController extends BaseControllerImpl<Factura, FacturaDTO, FacturaServiceImpl> {
    // Los métodos CRUD básicos se heredan de BaseControllerImpl
}

