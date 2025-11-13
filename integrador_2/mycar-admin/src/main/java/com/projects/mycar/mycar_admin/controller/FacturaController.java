package com.projects.mycar.mycar_admin.controller;

import com.projects.mycar.mycar_admin.domain.FacturaDTO;
import com.projects.mycar.mycar_admin.service.impl.FacturaServiceImpl;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/factura")
@Getter
public class FacturaController extends BaseControllerImpl<FacturaDTO, FacturaServiceImpl> {

    private String viewList = "";
    private String viewEdit = "";
    private String redirectList = "";

    @Override
    @GetMapping("/crear")
    public String crear(Model model) {
        // TODO Auto-generated method stub
        return null;
    }

}
