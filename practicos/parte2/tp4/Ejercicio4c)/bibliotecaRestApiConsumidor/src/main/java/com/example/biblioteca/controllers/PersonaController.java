package com.example.biblioteca.controllers;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.biblioteca.domain.dto.DomicilioDTO;
import com.example.biblioteca.domain.dto.LibroDTO;
import com.example.biblioteca.domain.dto.LocalidadDTO;
import com.example.biblioteca.domain.dto.PersonaDTO;
import com.example.biblioteca.service.impl.LibroServiceImpl;
import com.example.biblioteca.service.impl.LocalidadServiceImpl;
import com.example.biblioteca.service.impl.PersonaServiceImpl;

import lombok.Getter;

@Controller
@RequestMapping("/persona")
@Getter
public class PersonaController extends BaseControllerImpl<PersonaDTO, PersonaServiceImpl> {
	
	/*public PersonaController() {
		super(PersonaDTO.class);
	}*/
	
	@Autowired
	private LocalidadServiceImpl localidadService;
	
	@Autowired
	private LibroServiceImpl libroService;

	private String viewList = "view/persona/lPersonas";
	private String viewEdit = "view/persona/ePersonas";
	private String redirectList= "redirect:/persona/listar";
	
	@GetMapping("/crear")
	public String crear(Model model){
	    try {
	    	PersonaDTO persona = new PersonaDTO();
	    	DomicilioDTO domicilio = new DomicilioDTO();
	    	domicilio.setLocalidad(new LocalidadDTO());
	    	persona.setDomicilio(domicilio);
	    	persona.setLibros(new ArrayList<LibroDTO>());
	        model.addAttribute("isNew", true);
	    	model.addAttribute("entity", persona);
	    	
	    	List<LocalidadDTO> localidades = localidadService.findAll();
    	    model.addAttribute("localidades", localidades);
	    
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("msgError", "Error de Sistema"); 
	    }
	    
	    return viewEdit; 
	}
	
	@GetMapping("/searchByDni")
	public String searchByDni(@RequestParam(defaultValue = "0") int dni, Model model) {
		
		if (dni == 0) {
			return redirectList;
		}
		
		try {
			
			PersonaDTO persona = servicio.searchByDni(dni);
			
			if (persona == null) {
				model.addAttribute("msgError", "No se encontró la persona con ese dni");
			}else {
				model.addAttribute("lista", persona);
			}
			
		} catch (Exception e) {
			model.addAttribute("msgError", "Error al realizar la búsqueda");
		}
		
		return viewList;
	}
	
	@GetMapping("/detalle/{id}")
	public String verDetallePersona(@PathVariable Long id, Model model) {
	    try {
	        PersonaDTO persona = servicio.findById(id);
	        List<LibroDTO> librosDisponibles = libroService.findAll(); // mientras tanto uso findAll pero deberia ser un metodo que devuelva libros con estado DISPONIBLE
	        
	        model.addAttribute("persona", persona);
	        model.addAttribute("librosDisponibles", librosDisponibles);
	        model.addAttribute("msgExito", model.getAttribute("msgExito"));
	        model.addAttribute("msgError", model.getAttribute("msgError"));
	        
	    } catch (Exception e) {
	        model.addAttribute("msgError", "No se pudo cargar el detalle de la persona");
	        e.printStackTrace();
	    }
	    return "/view/persona/detalle";
	}

	
	@PostMapping("/asignarLibro")
	public String asignarLibro(@RequestParam("idPersona") Long idPersona, @RequestParam("idLibro") Long idLibro, RedirectAttributes redirectAttributes) {
	    try {
	        servicio.asignarLibro(idPersona, idLibro);
	        redirectAttributes.addFlashAttribute("msgExito", "El libro fue asignado correctamente.");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("msgError", e.getMessage());
	    }
	    return "redirect:/persona/detalle/" + idPersona;
	}



	
}
