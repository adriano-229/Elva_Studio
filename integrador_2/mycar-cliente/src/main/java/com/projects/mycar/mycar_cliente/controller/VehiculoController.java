package com.projects.mycar.mycar_cliente.controller;

import com.projects.mycar.mycar_cliente.domain.VehiculoDTO;
import com.projects.mycar.mycar_cliente.domain.enums.EstadoVehiculo;
import com.projects.mycar.mycar_cliente.service.impl.VehiculoServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/flota")
@Getter
public class VehiculoController extends BaseControllerImpl<VehiculoDTO, VehiculoServiceImpl> {

    @Autowired
    private VehiculoServiceImpl service;


    @GetMapping("/disponibles")
    public String listarDisponibles(Model model) {
        List<VehiculoDTO> disponibles = service.buscarPorEstado(EstadoVehiculo.Disponible);
        model.addAttribute("vehiculos", disponibles);
        return "flota"; 
    }

    @Override
    public String crear(Model model) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getViewList() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getViewEdit() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getRedirectList() {
        // TODO Auto-generated method stub
        return null;
    }

}
