package com.projects.mycar.mycar_admin.controller;

import com.projects.mycar.mycar_admin.domain.UsuarioDTO;
import com.projects.mycar.mycar_admin.service.impl.UsuarioServiceImpl;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
@Getter
public class UsuarioController extends BaseControllerImpl<UsuarioDTO, UsuarioServiceImpl> {

    private String viewList = "view/usuario/listar";
    private String viewEdit = "";
    private String redirectList = "";

    @Override
    @GetMapping("/crear")
    public String crear(Model model) {
        // TODO Auto-generated method stub
        return null;
    }

}
