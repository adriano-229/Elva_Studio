package com.example.mecanico_app.web;

import com.example.mecanico_app.domain.Mecanico;
import com.example.mecanico_app.domain.TipoReparacion;
import com.example.mecanico_app.domain.Vehiculo;
import com.example.mecanico_app.service.TallerMecanicoFacade;
import com.example.mecanico_app.web.dto.RegistroReparacionForm;
import com.example.mecanico_app.web.dto.RegistroVehiculoForm;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TallerController {

    private final TallerMecanicoFacade facade;

    public TallerController(TallerMecanicoFacade facade) {
        this.facade = facade;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("vehiculos", facade.listarVehiculosActivos());
        return "dashboard";
    }

    @GetMapping("/vehiculos/nuevo")
    public String mostrarFormularioVehiculo(Model model) {
        if (!model.containsAttribute("vehiculoForm")) {
            model.addAttribute("vehiculoForm", new RegistroVehiculoForm());
        }
        model.addAttribute("clientes", facade.listarClientesActivos());
        return "vehiculos-nuevo";
    }

    @PostMapping("/vehiculos")
    public String registrarVehiculo(@Valid @ModelAttribute("vehiculoForm") RegistroVehiculoForm form,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.vehiculoForm", bindingResult);
            redirectAttributes.addFlashAttribute("vehiculoForm", form);
            redirectAttributes.addFlashAttribute("error", "Revise los datos ingresados.");
            return "redirect:/vehiculos/nuevo";
        }

        try {
            facade.registrarVehiculo(
                    form.getPatente().toUpperCase(),
                    form.getMarca(),
                    form.getModelo(),
                    UUID.fromString(form.getClienteId()));
            redirectAttributes.addFlashAttribute("exito", "Vehículo registrado correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("vehiculoForm", form);
        }
        return "redirect:/vehiculos/nuevo";
    }

    @GetMapping("/vehiculos/{vehiculoId}")
    public String detalleVehiculo(@PathVariable UUID vehiculoId, Model model, Authentication authentication) {
        Vehiculo vehiculo = facade.obtenerVehiculo(vehiculoId);
        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("historial", facade.listarHistorialPorVehiculo(vehiculoId));
        boolean esMecanico = tieneRol(authentication, "ROLE_MECANICO");
        Mecanico mecanicoActual = null;
        if (esMecanico && authentication != null) {
            mecanicoActual = facade.obtenerMecanicoPorUsuario(authentication.getName());
            if (mecanicoActual == null) {
                model.addAttribute("alertaMecanico", "El usuario logueado no está asociado a un mecánico activo.");
            }
        }

        RegistroReparacionForm form;
        if (model.containsAttribute("reparacionForm")) {
            form = (RegistroReparacionForm) model.asMap().get("reparacionForm");
        } else {
            form = new RegistroReparacionForm();
        }
        form.setVehiculoId(vehiculoId.toString());
        if (esMecanico && mecanicoActual != null) {
            form.setMecanicoId(mecanicoActual.getId().toString());
        }
        model.addAttribute("reparacionForm", form);

        List<Mecanico> mecanicos = (esMecanico && mecanicoActual != null)
                ? List.of(mecanicoActual)
                : facade.listarMecanicosActivos();
        model.addAttribute("mecanicos", mecanicos);
        model.addAttribute("mecanicoActual", mecanicoActual);
        model.addAttribute("esMecanico", esMecanico);
        model.addAttribute("tiposReparacion", TipoReparacion.values());
        return "vehiculos-detalle";
    }

    @PostMapping("/reparaciones")
    public String registrarReparacion(@Valid @ModelAttribute("reparacionForm") RegistroReparacionForm form,
                                      BindingResult result,
                                      RedirectAttributes redirectAttributes,
                                      Authentication authentication) {
        UUID vehiculoId = UUID.fromString(form.getVehiculoId());
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reparacionForm", result);
            redirectAttributes.addFlashAttribute("reparacionForm", form);
            redirectAttributes.addFlashAttribute("error", "No se pudo registrar la reparación.");
            return "redirect:/vehiculos/" + vehiculoId;
        }
        try {
            UUID mecanicoId;
            if (tieneRol(authentication, "ROLE_MECANICO")) {
                Mecanico mecanicoActual = facade.obtenerMecanicoPorUsuario(authentication.getName());
                if (mecanicoActual == null) {
                    throw new IllegalArgumentException("El usuario no está asociado a un mecánico activo.");
                }
                mecanicoId = mecanicoActual.getId();
                form.setMecanicoId(mecanicoId.toString());
            } else {
                mecanicoId = UUID.fromString(form.getMecanicoId());
            }
            facade.registrarReparacion(
                    vehiculoId,
                    mecanicoId,
                    form.getTipoReparacion(),
                    form.getDescripcion());
            redirectAttributes.addFlashAttribute("exito", "Reparación registrada correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("reparacionForm", form);
        }
        return "redirect:/vehiculos/" + vehiculoId;
    }

    private boolean tieneRol(Authentication authentication, String rol) {
        return authentication != null
                && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(rol));
    }
}
