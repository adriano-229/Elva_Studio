package com.example.contactosApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.contactosApp.domain.Base;
import com.example.contactosApp.domain.ContactoTelefonico;
import com.example.contactosApp.domain.Usuario;
import com.example.contactosApp.domain.dto.BaseDTO;
import com.example.contactosApp.domain.dto.ContactoCorreoElectronicoDTO;
import com.example.contactosApp.domain.dto.ContactoDTO;
import com.example.contactosApp.domain.dto.ContactoTelefonicoDTO;
import com.example.contactosApp.domain.dto.EmpresaDTO;
import com.example.contactosApp.domain.dto.PersonaDTO;
import com.example.contactosApp.domain.dto.UsuarioDTO;
import com.example.contactosApp.service.impl.BaseServiceImpl;
import com.example.contactosApp.service.impl.UsuarioServiceImpl;


public abstract class BaseControllerImpl<E extends Base, D extends BaseDTO, S extends BaseServiceImpl<E, D, Long>> implements BaseController<D, Long>{
	
	@Autowired
	protected S servicio;
	
	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	protected abstract String getViewList();
	protected abstract String getViewEdit();
	protected abstract String getRedirectList();
	
	@GetMapping("/listar")
	public String listar(Model model){
		try {
			
			List<D> entities = servicio.findAll();
			System.out.println("entidades: " + entities);
			model.addAttribute("lista", entities);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msgError", "Error de Sistema"); 
		}
		
		return getViewList();
	}
	
	@PostMapping("/crear")
	public String guardar(@ModelAttribute D entity, Model model) {
	    try {
	    	
	    	servicio.save(entity);
	    	model.addAttribute("msgExito", "Registro creado correctamente");
	    	System.out.println("estoy en el pos de crear");
	    	if (entity instanceof ContactoDTO contacto) {
		    	System.out.println("si es instance de contactodto");
		    	if (contacto.getEmpresa() != null) {
		    		return "redirect:/empresa/detalle/" + contacto.getEmpresa().getId();
		    	}
		    	System.out.println("empresa es null");
		    	
		    	if (contacto.getPersona() != null) {
		    		try {
						UsuarioDTO usuario = usuarioService.findByPersona(contacto.getPersona().getId());
						return "redirect:/persona/detalle/" + usuario.getPersona().getId();
					
		    		} catch (Exception e) {
						e.printStackTrace();
					}
		    		
		    	}
		    	
		    }
	        
	        if (entity instanceof PersonaDTO persona) {
	    		return "redirect:/persona/detalle/" + persona.getId();
	    	}
	    	
	    	if (entity instanceof UsuarioDTO usuario) {
	    		return "redirect:/persona/detalle/" + usuario.getPersona().getId();
	    	}
	    	
	    	
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
	    	D entity = servicio.findById(id);

	        if (entity != null) {
	        	
	        	model.addAttribute("entity", entity);
	            model.addAttribute("isNew", false);
	            
	        } else {
	            
	            model.addAttribute("msgError", "Entidad no encontrada");
	            return getRedirectList(); 
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("msgError", "Error de Sistema"); 
	    }
	    
	    return getViewEdit(); 
	}
	
	@PostMapping("/modificar")
	public String guardarModificacion(@ModelAttribute D entity, Model model) {
	    try {
	        servicio.update(entity.getId(), entity); // suponiendo que update recibe ID y entidad
	        model.addAttribute("msgExito", "Registro actualizado correctamente");
	        
	        if (entity instanceof ContactoDTO contacto) {
		    	
		    	if (contacto.getEmpresa() != null) {
		    		return "redirect:/empresa/detalle/" + contacto.getEmpresa().getId();
		    	}
		    	
		    	if (contacto.getPersona() != null) {
		    		try {
						UsuarioDTO usuario = usuarioService.findByPersona(contacto.getPersona().getId());
						return "redirect:/usuario/detalle/" + usuario.getId();
					
		    		} catch (Exception e) {
						e.printStackTrace();
					}
		    		
		    	}
		    	
		    }
	        
	        if (entity instanceof PersonaDTO persona) {
	    		return "redirect:/persona/detalle/" + persona.getId();
	    	}
	    	
	    	if (entity instanceof UsuarioDTO usuario) {
	    		return "redirect:/persona/detalle/" + usuario.getPersona().getId();
	    	}
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("msgError", "Error al actualizar registro");
	    }
	    
	    return getRedirectList(); 
	}

	
	@GetMapping("/eliminar/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
	    try {
	        
	    	D entity = servicio.findById(id);
	        servicio.delete(id);
	        
	        redirectAttributes.addFlashAttribute("msgExito", "Registro eliminado correctamente");
	        
	        if (entity instanceof ContactoDTO contacto) {
		    	
		    	if (contacto.getEmpresa() != null) {
		    		return "redirect:/empresa/detalle/" + contacto.getEmpresa().getId();
		    	}
		    	
		    	if (contacto.getPersona() != null) {
		    		try {
						UsuarioDTO usuario = usuarioService.findByPersona(contacto.getPersona().getId());
						return "redirect:/usuario/detalle/" + usuario.getId();
					
		    		} catch (Exception e) {
						e.printStackTrace();
					}
		    		
		    	}
		    	
		    }
	        
	        if (entity instanceof PersonaDTO persona) {
	    		return "redirect:/persona/detalle/" + persona.getId();
	    	}
	    	
	    	if (entity instanceof UsuarioDTO usuario) {
	    		return "redirect:/persona/detalle/" + usuario.getPersona().getId();
	    	}
	        
	        
	    } catch (RuntimeException e) {
	    	redirectAttributes.addFlashAttribute("msgError", e.getMessage());
	    } catch (Exception e) {
	        e.printStackTrace();
	        redirectAttributes.addFlashAttribute("msgError", "Ocurrió un error inesperado al eliminar el registro");
	    }
	    
	    
	    return getRedirectList(); 
	}

	
	
	
	
	
	
}