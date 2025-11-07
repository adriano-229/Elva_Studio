package com.projects.mycar.mycar_cliente.controller;

import com.projects.mycar.mycar_cliente.domain.BaseDTO;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.Serializable;


public interface BaseController<E extends BaseDTO, ID extends Serializable> {

    String listar(Model model);

    String crear(Model model);

    String guardar(E entity, Model model);

    String modificar(ID id, Model model);

    String delete(ID id, RedirectAttributes redirectAttributes);
}
