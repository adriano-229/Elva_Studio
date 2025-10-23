package com.example.biblioteca.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.biblioteca.domain.dto.LibroDTO;
import com.example.biblioteca.domain.enums.TipoBusqueda;
import com.example.biblioteca.service.impl.AutorServiceImpl;
import com.example.biblioteca.service.impl.LibroServiceImpl;

import lombok.Getter;

@Controller
@RequestMapping("/libro")
@Getter
public class LibroController extends BaseControllerImpl<LibroDTO, LibroServiceImpl>{
	
	/*public LibroController() {
		super(LibroDTO.class);
	}*/
	
	@Autowired
	private AutorServiceImpl autorService;

	private String viewList = "view/libro/lLibros";
	private String viewEdit = "view/libro/elibros";
	private String redirectList= "redirect:/libro/listar";
	
	
	@GetMapping("/crear")
	public String crear(Model model){
	    try {
	    	LibroDTO libro = new LibroDTO();
	    	model.addAttribute("isNew", true);
	        model.addAttribute("entity", libro);
	        
	        //agregamos la lista de autores al modelo para que se muestre en el select
	        model.addAttribute("autores", autorService.findAll());
	    
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("msgError", "Error de Sistema"); 
	    }
	    
	    return getViewEdit(); 
	}
	
	@GetMapping("/searchByTitulo")
	public String searchByTitulo(@RequestParam String titulo, Model model) {
		
		try {
			
			LibroDTO libro = servicio.searchByTitulo(titulo);
			
			if (libro == null) {
				model.addAttribute("msgError", "No se encontró el libro");
			}else {
				model.addAttribute("libro", libro);
			}
			
		} catch (Exception e) {
			model.addAttribute("msgError", "Error al realizar la búsqueda");
		}
		
		return viewList;
	}
	
	@GetMapping("/search")
	public String search(@RequestParam String filtro, Model model) {
		
		try {
			
			List<LibroDTO> lista = servicio.buscarPorFiltro(filtro);
			
			if (lista == null || lista.isEmpty()) {
				model.addAttribute("msgError", "No se encontró ninguna referencia");
				lista = new ArrayList<LibroDTO>();
			}
			
			model.addAttribute("lista", lista);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msgError", "Error al realizar la búsqueda");
			model.addAttribute("lista", new ArrayList<>());
		}
		
		return viewList;
	}
	
	@GetMapping("/search")
	public String search(@RequestParam TipoBusqueda tipo, @RequestParam String filtro, Model model) {
		
		List<LibroDTO> lista = null;
		
		try {
			
			if (tipo.name() == "busquedaPorAutor") {
				// Patron iterator
				lista = servicio.buscarPorAutor(filtro);
			
			} else {
				// Patron strategy
				lista = servicio.buscarPorFiltro(tipo, filtro);
			}
			
			
			if (lista == null || lista.isEmpty()) {
				model.addAttribute("msgError", "No se encontró ninguna referencia");
				lista = new ArrayList<LibroDTO>();
			}
			
			model.addAttribute("lista", lista);
			model.addAttribute("filtro", filtro);
			model.addAttribute("tipoBusqueda", tipo);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msgError", "Error al realizar la búsqueda");
			model.addAttribute("lista", new ArrayList<>());
		}
		
		return viewList;
	}
	
	@GetMapping("/autor")
	public String buscarPorAutor(@RequestParam String autor, Model model) {
	    try {
	        List<LibroDTO> libros = servicio.buscarPorAutor(autor);
	        model.addAttribute("libros", libros);
	        model.addAttribute("autorBuscado", autor);
	        return "libros/lista"; // nombre de la vista
	    } catch (Exception e) {
	        model.addAttribute("error", e.getMessage());
	        return "error";
	    }
	}

	
	@GetMapping("/detalle/{id}")
	public String verDetalleLibro(@PathVariable Long id, Model model) {
	    try {
	        LibroDTO libro = servicio.findById(id);
	        
	        model.addAttribute("libro", libro);
	    
	    } catch (Exception e) {
	        model.addAttribute("msgError", "No se pudo cargar el detalle del libro");
	        e.printStackTrace();
	        
	    }
	    return "/view/libro/detalle";
	}

}
