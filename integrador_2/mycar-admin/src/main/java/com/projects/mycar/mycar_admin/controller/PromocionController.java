package com.projects.mycar.mycar_admin.controller;

import com.example.mycar.mycar_admin.domain.ConfiguracionPromocionDTO;
import com.projects.mycar.mycar_admin.service.impl.PromocionServiceImpl;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/promocion")
@Getter
public class PromocionController extends BaseControllerImpl<ConfiguracionPromocionDTO, PromocionServiceImpl> {

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
