package com.projects.mycar.mycar_admin.controller;

import com.projects.mycar.mycar_admin.domain.VehiculoDTO;
import com.projects.mycar.mycar_admin.service.impl.VehiculoServiceImpl;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vehiculo")
@Getter
public class VehiculoController extends BaseControllerImpl<VehiculoDTO, VehiculoServiceImpl> {

    private static final String VIEW_LIST = "view/vehiculo/listar";
    private static final String VIEW_FORM = "view/vehiculo/form";
    private static final String VIEW_DETAIL = "view/vehiculo/detalle";
    private static final String REDIRECT_LIST = "redirect:/vehiculo/listar";

    @Override
    protected String getViewList() {
        return VIEW_LIST;
    }

    @Override
    protected String getViewEdit() {
        return VIEW_FORM;
    }

    @Override
    protected String getRedirectList() {
        return REDIRECT_LIST;
    }

    @Override
    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("entity", new VehiculoDTO());
        model.addAttribute("isNew", true);
        return VIEW_FORM;
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        try {
            VehiculoDTO vehiculo = servicio.findById(id);
            model.addAttribute("vehiculo", vehiculo);
        } catch (Exception e) {
            model.addAttribute("msgError", "No se pudo cargar el detalle del vehículo");
        }
        return VIEW_DETAIL;
    }

}
