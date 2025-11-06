package com.example.biblioteca.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.biblioteca.domain.dto.AutorDTO;
import com.example.biblioteca.domain.dto.LibroDTO;
import com.example.biblioteca.service.impl.AutorServiceImpl;

import lombok.Getter;

@Controller
@RequestMapping("/autor")
@Getter
public class AutorController extends BaseControllerImpl<AutorDTO, AutorServiceImpl>{
	
	/*public AutorController() {
		super(AutorDTO.class);
	}*/

	private String viewList = "view/autor/lAutores";
	private String viewEdit = "view/autor/eAutores";
	private String redirectList= "redirect:/autor/listar";
	
	@GetMapping("/crear")
	public String crear(Model model){
	    try {
	    	AutorDTO autor = new AutorDTO();
	    	model.addAttribute("isNew", true);
	    	model.addAttribute("entity", autor);
	    
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("msgError", "Error de Sistema"); 
	    }
	    
	    return getViewEdit(); 
	}
	
	@GetMapping("/search")
	public String search(@RequestParam String filtro, Model model) {
		
		try {
			
			List<AutorDTO> lista = servicio.searchByNombreApellido(filtro);
			
			if (lista == null || lista.isEmpty()) {
				model.addAttribute("msgError", "No se encontró el autor");
				lista = new ArrayList<AutorDTO>();
			}
			
			model.addAttribute("lista", lista);
			
		} catch (Exception e) {
			model.addAttribute("msgError", "Error al realizar la búsqueda");
			model.addAttribute("lista", new ArrayList<>());
		}
		
		return viewList;
	}
	
	@GetMapping("/detalle/{id}")
	public String verDetalleLibro(@PathVariable Long id, Model model) {
	    try {
	        AutorDTO autor = servicio.findById(id);
	        System.out.println("AUTOR: " + autor);
	        model.addAttribute("autor", autor);
	    
	    } catch (Exception e) {
	        model.addAttribute("msgError", "No se pudo cargar el detalle del libro");
	        e.printStackTrace();
	        
	    }
	    return "/view/autor/detalle";
	}
	
	@PostMapping("/clonar/{idAutor}")
    public String clonarAutor(@PathVariable Long idAutor, RedirectAttributes redirectAttributes) {
        try {
            AutorDTO nuevoAutor = servicio.clonarAutor(idAutor);
            
            redirectAttributes.addFlashAttribute("msgExito", "Autor clonado correctamente");
            return "redirect:/autor/listar";
            //detalle/" + nuevoAutor.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msgError", e.getMessage());
            return "redirect:/autor/listar";
            //detalle/" + idAutor;
        }
    }
	
	
}
