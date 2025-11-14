package com.projects.mycar.mycar_admin.controller;

import com.projects.mycar.mycar_admin.domain.DocumentacionDTO;
import com.projects.mycar.mycar_admin.service.impl.DocumentacionServiceImpl;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/documentacion")
@Getter
public class DocumentacionController extends BaseControllerImpl<DocumentacionDTO, DocumentacionServiceImpl>{

    private final String viewList = "";
    private final String viewEdit = "";
    private final String redirectList = "redirect:/alquiler/listar";
	
	@Override
	public String crear(Model model) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@PostMapping("/modificar")
    public String guardarModificacion(@ModelAttribute DocumentacionDTO entity, Model model, RedirectAttributes redirectAttributes) {
        try {
        	System.out.println("ID: " + entity.getId());
            servicio.updateDocumentacion(entity); 
            redirectAttributes.addFlashAttribute("msgExito", "Registro actualizado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("msgError", "Error al actualizar registro");
        }

        return getRedirectList();
    }

	

}
