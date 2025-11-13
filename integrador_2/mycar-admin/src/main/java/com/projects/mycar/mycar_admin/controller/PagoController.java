package com.projects.mycar.mycar_admin.controller;

import com.projects.mycar.mycar_admin.service.impl.RespuestaPagoServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pago")
@Getter
public class PagoController {

    @Autowired
    private RespuestaPagoServiceImpl pagoService;

    private String viewList = "";
    private String viewEdit = "";
    private String redirectList = "";

    @GetMapping("/crear")
    public String crear(Model model) {
        // TODO Auto-generated method stub
        return null;
    }


}
