package com.projects.mycar.mycar_admin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.mycar.mycar_admin.domain.VehiculoDTO;
import com.example.mycar.mycar_admin.domain.VehiculoFormDTO;
import com.projects.mycar.mycar_admin.mapper.VehiculoDTOMapper;
import com.projects.mycar.mycar_admin.service.impl.VehiculoServiceImpl;

import lombok.Getter;

@Controller
@RequestMapping("/vehiculo")
@Getter
public class VehiculoController extends BaseControllerImpl<VehiculoDTO, VehiculoServiceImpl>{
	
	@Autowired
	private VehiculoServiceImpl service;
	
	@Autowired
	private VehiculoDTOMapper mapperDTO;
	
	private String viewList = "view/vehiculo/listar";
	private String viewEdit = "";
	private String redirectList= "";

	
	
	@Override
	@GetMapping("/nuevo")
	public String crear(Model model) {
		model.addAttribute("vehiculoForm", new VehiculoFormDTO());
        return "view/vehiculo/agregar";
	}
	
	
	@PostMapping("/guardar")
	public String guardarVehiculo(@ModelAttribute("vehiculoForm") VehiculoFormDTO vehiculoForm,
	                              @RequestParam("file") MultipartFile file,
	                              Model model,
	                              RedirectAttributes redirectAttributes) {

	    try {

	        //  Validar imagen

	        if (file == null || file.isEmpty()) {
	            model.addAttribute("error", "Debe subir una imagen.");
	            return "view/vehiculo/agregar";
	        }

	        vehiculoForm.setMime(file.getContentType());
	        vehiculoForm.setContenido(file.getBytes());

	        //  Validar patente

	        boolean disponible = service.validarPorPatente(vehiculoForm.getPatente());

	        if (!disponible) {  
	            model.addAttribute("vehiculoForm", vehiculoForm);
	            model.addAttribute("error", "La patente ingresada ya existe. Ingrese otra.");
	            return "view/vehiculo/agregar";
	        }

	        //  Validar anio

	        Long anio = vehiculoForm.getAnio();
	        int anioActual = java.time.LocalDate.now().getYear();

	        if (anio == null || anio < 1950 || anio > anioActual + 1) {
	            model.addAttribute("vehiculoForm", vehiculoForm);
	            model.addAttribute("error", "El año del vehículo no es válido.");
	            return "view/vehiculo/agregar";
	        }
	        
	        // validar fecha

	        if (vehiculoForm.getFechaDesde() == null || vehiculoForm.getFechaHasta() == null) {
	            model.addAttribute("vehiculoForm", vehiculoForm);
	            model.addAttribute("error", "Debe ingresar fechas válidas.");
	            return "view/vehiculo/agregar";
	        }

	        if (vehiculoForm.getFechaDesde().isBefore(java.time.LocalDate.now())) {
	            model.addAttribute("vehiculoForm", vehiculoForm);
	            model.addAttribute("error", "La fecha es incorrecta.");
	            return "view/vehiculo/agregar";
	        }

	        if (vehiculoForm.getFechaHasta().isBefore(vehiculoForm.getFechaDesde())) {
	            model.addAttribute("vehiculoForm", vehiculoForm);
	            model.addAttribute("error", "La fecha es incorrecta.");
	            return "view/vehiculo/agregar";
	        }

	        // todo ok, lo guardo

	        VehiculoDTO vehiculoDTO = mapperDTO.toEntityDTO(vehiculoForm);
	        service.save(vehiculoDTO);
	        
	        redirectAttributes.addFlashAttribute("success", "Vehículo agregado correctamente.");
	        return "redirect:/vehiculo/listar";

	    } catch (Exception e) {
	        model.addAttribute("vehiculoForm", vehiculoForm);
	        model.addAttribute("error", "Error al guardar el vehículo: " + e.getMessage());
	        return "view/vehiculo/agregar";
	    }
	}
	
	// editar un vehiculo
	@GetMapping("/editar/{id}")
	public String editarVehiculo(@PathVariable Long id, Model model) {
		
		try {
			
			Optional<VehiculoDTO> optvehiculoDTO = service.buscarPorId(id);
			
			if (optvehiculoDTO.isPresent()) {
				VehiculoFormDTO vehiculoForm = mapperDTO.toFormDTO(optvehiculoDTO.get()); 
		        model.addAttribute("vehiculoForm", vehiculoForm);
		        return "view/vehiculo/editar";
			} else {
				model.addAttribute("error", "No se encontró el vehículo con ID " + id);
	            return "redirect:/vehiculo/listar";
			}
	        
			
		} catch (Exception e) {
			model.addAttribute("error", "No se pudo cargar el vehículo: " + e.getMessage());
	        return "redirect:/vehiculo/listar";
		}
		
	}
	
	@PostMapping("/actualizar")
	public String actualizarVehiculo(@ModelAttribute VehiculoFormDTO vehiculoForm, 
										RedirectAttributes redirectAttributes,
										 Model model) throws Exception {
	    
		VehiculoDTO vehiculoDTO = mapperDTO.toEntityDTOUpdate(vehiculoForm);
		
		Optional<VehiculoDTO> originalOpt = service.buscarPorId(vehiculoDTO.getId());
	    if(originalOpt.isPresent()) {
	        VehiculoDTO original = originalOpt.get();

	        // Si el usuario no cambió fechaDesde, usamos la original
	        if(vehiculoForm.getFechaDesde() == null) {
	            vehiculoDTO.getCaracteristicaVehiculo()
	                      .getCostoVehiculo()
	                      .setFechaDesde(original.getCaracteristicaVehiculo()
	                                             .getCostoVehiculo()
	                                             .getFechaDesde());
	        }

	        // Si el usuario no cambió fechaHasta, usamos la original
	        if(vehiculoForm.getFechaHasta() == null) {
	            vehiculoDTO.getCaracteristicaVehiculo()
	                      .getCostoVehiculo()
	                      .setFechaHasta(original.getCaracteristicaVehiculo()
	                                             .getCostoVehiculo()
	                                             .getFechaHasta());
	        }
	    }
		
        try {
			service.update(vehiculoDTO.getId(), vehiculoDTO);
			redirectAttributes.addFlashAttribute("success", "Vehículo actualizado correctamente.");
	        return "redirect:/vehiculo/listar";
		} catch (Exception e) {
	        model.addAttribute("vehiculoForm", vehiculoForm);
	        model.addAttribute("error", "Error al actualizar el vehículo: " + e.getMessage());
	        return "view/vehiculo/editar";
	    }

	}
	
	@Override
	@GetMapping("/eliminar/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
	    try {
	        Optional<VehiculoDTO> optVehiculo = service.buscarPorId(id);

	        if (optVehiculo.isPresent()) {
	            VehiculoDTO vehiculo = optVehiculo.get();

	            // Si hay relaciones, eliminarlas primero según corresponda
	            if (vehiculo.getCaracteristicaVehiculo() != null) {
	                if (vehiculo.getCaracteristicaVehiculo().getCostoVehiculo() != null) {
	                    service.deleteCostoVehiculo(vehiculo.getCaracteristicaVehiculo().getCostoVehiculo().getId());
	                }
	                if (vehiculo.getCaracteristicaVehiculo().getImagen() != null) {
	                    service.deleteImagenVehiculo(vehiculo.getCaracteristicaVehiculo().getImagen().getId());
	                }
	                service.deleteCaracteristicaVehiculo(vehiculo.getCaracteristicaVehiculo().getId());
	            }

	            // Finalmente eliminar el Vehículo
	            service.delete(id);

	            redirectAttributes.addFlashAttribute("success", "Vehículo eliminado correctamente.");
	        } else {
	            redirectAttributes.addFlashAttribute("error", "No se encontró el vehículo con ID " + id);
	        }

	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el vehículo: " + e.getMessage());
	    }

	    return "redirect:/vehiculo/listar";
	}




}
