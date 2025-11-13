package com.projects.mycar.mycar_admin.controller;

import com.projects.mycar.mycar_admin.domain.ClienteDTO;
import com.projects.mycar.mycar_admin.service.impl.ClienteServiceImpl;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente")
@Getter
public class ClienteController extends BaseControllerImpl<ClienteDTO, ClienteServiceImpl> {

    private String viewList = "";
    private String viewEdit = "";
    private String redirectList = "";

    @Override
    public String crear(Model model) {
        // TODO Auto-generated method stub
        return null;
    }

    @GetMapping("/search")
    public String buscarPorDni(String numeroDocumento, Model model) {

        try {

            ClienteDTO cliente = servicio.buscarPorDni(numeroDocumento);

            if (cliente == null) {
                model.addAttribute("msgError", "El cliente no se encuentra registrado");
            } else {
                model.addAttribute("cliente", cliente);
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msgError", "error al crear alquiler con cotizacion");
        }
        return "";
    }


}
