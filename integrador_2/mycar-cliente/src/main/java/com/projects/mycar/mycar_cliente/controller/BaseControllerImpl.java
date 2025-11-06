package com.projects.mycar.mycar_cliente.controller;

import com.projects.mycar.mycar_cliente.domain.BaseDTO;
import com.projects.mycar.mycar_cliente.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


public abstract class BaseControllerImpl<E extends BaseDTO, S extends BaseServiceImpl<E, Long>> implements BaseController<E, Long> {

    @Autowired
    protected S servicio;

    protected abstract String getViewList();

    protected abstract String getViewEdit();

    protected abstract String getRedirectList();

    //private Class<E> entityClass;

    // Constructor para pasar la clase de la entidad
    /*public BaseControllerImpl(Class<E> entityClass) {
        this.entityClass = entityClass;
    }*/

    @GetMapping("/listar")
    public String listar(Model model) {
        try {

            List<E> entities = servicio.findAll();
            model.addAttribute("lista", entities);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msgError", "Error de Sistema");
        }

        return getViewList();
    }

    @PostMapping("/crear")
    public String guardar(@ModelAttribute E entity, Model model) {
        try {

            servicio.save(entity);
            model.addAttribute("msgExito", "Registro creado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msgError", "Error al guardar registro");
        }

        return getRedirectList();
    }


    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, Model model) {
        try {
            // Buscar la entidad por id
            E entity = servicio.findById(id);
            model.addAttribute("entity", entity);
            model.addAttribute("isNew", false);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msgError", "Error de Sistema");
        }

        return getViewEdit();
    }

    @PostMapping("/modificar")
    public String guardarModificacion(@ModelAttribute E entity, Model model) {
        try {
            servicio.update(entity.getId(), entity); // suponiendo que update recibe ID y entidad
            model.addAttribute("msgExito", "Registro actualizado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msgError", "Error al actualizar registro");
        }

        return getRedirectList();
    }


    @GetMapping("/eliminar/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {

            servicio.delete(id);
            redirectAttributes.addFlashAttribute("msgExito", "Registro eliminado correctamente");

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("msgError", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("msgError", "Ocurrió un error inesperado al eliminar el registro");
        }

        return getRedirectList();
    }

}
