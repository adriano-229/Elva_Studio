package com.projects.mycar.mycar_admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.mycar.mycar_admin.domain.DocumentacionDTO;
import com.projects.mycar.mycar_admin.service.impl.DocumentacionServiceImpl;

import lombok.Getter;

@Controller
@RequestMapping("/documentacion")
@Getter
public class DocumentacionController extends BaseControllerImpl<DocumentacionDTO, DocumentacionServiceImpl>{
	
	private String viewList = "";
	private String viewEdit = "";
	private String redirectList= "redirect:/alquiler/listar";
	
	@Override
	public String crear(Model model) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 @PostMapping("/modificar")
	    public String guardarModificacion(@ModelAttribute DocumentacionDTO entity, Model model) {
	        try {
	        	System.out.println("ID: " + entity.getId());
	            servicio.updateDocumentacion(entity); 
	            model.addAttribute("msgExito", "Registro actualizado correctamente");
	        } catch (Exception e) {
	            e.printStackTrace();
	            model.addAttribute("msgError", "Error al actualizar registro");
	        }

	        return getRedirectList();
	    }

	

}
