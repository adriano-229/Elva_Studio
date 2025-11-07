package com.example.mycar.controller;

import com.example.mycar.dto.FacturaDTO;
import com.example.mycar.entities.Factura;
import com.example.mycar.services.impl.FacturaServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController extends BaseControllerImpl<Factura, FacturaDTO, FacturaServiceImpl> {
    // Los métodos CRUD básicos se heredan de BaseControllerImpl
}

