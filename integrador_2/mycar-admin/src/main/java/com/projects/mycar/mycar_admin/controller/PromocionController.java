package com.projects.mycar.mycar_admin.controller;

import com.projects.mycar.mycar_admin.domain.ConfiguracionPromocionDTO;
import com.projects.mycar.mycar_admin.service.impl.PromocionServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/promociones")
public class PromocionController extends BaseControllerImpl<ConfiguracionPromocionDTO, PromocionServiceImpl> {

    private static final String VIEW_CONFIGURAR = "view/promociones/configurar";
    private static final String VIEW_ACTIVA = "view/promociones/ver-activa";
    private static final String VIEW_ENVIO = "view/promociones/envio-manual";
    private static final String REDIRECT_CONFIG = "redirect:/promociones/configurar";

    @Override
    protected String getViewList() {
        return VIEW_CONFIGURAR;
    }

    @Override
    protected String getViewEdit() {
        return VIEW_CONFIGURAR;
    }

    @Override
    protected String getRedirectList() {
        return REDIRECT_CONFIG;
    }

    @Override
    @GetMapping("/crear")
    public String crear(Model model) {
        return "redirect:/promociones/configurar";
    }

    @GetMapping("/configurar")
    public String mostrarFormulario(Model model) {
        try {
            ConfiguracionPromocionDTO configuracionActual = servicio.obtenerConfiguracionActiva();
            model.addAttribute("configuracionActual", configuracionActual);

            if (!model.containsAttribute("configuracion")) {
                ConfiguracionPromocionDTO configuracionFormulario =
                        configuracionActual != null ? configuracionActual : new ConfiguracionPromocionDTO();
                model.addAttribute("configuracion", configuracionFormulario);
            }

        } catch (Exception e) {
            model.addAttribute("msgError", e.getMessage());
            if (!model.containsAttribute("configuracion")) {
                model.addAttribute("configuracion", new ConfiguracionPromocionDTO());
            }
        }

        return VIEW_CONFIGURAR;
    }

    @PostMapping("/configurar")
    public String configurar(@ModelAttribute("configuracion") ConfiguracionPromocionDTO configuracion,
                             RedirectAttributes redirectAttributes) {
        try {
            servicio.configurarPromocion(configuracion);
            redirectAttributes.addFlashAttribute("msgExito", "Promoción configurada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msgError", e.getMessage());
            redirectAttributes.addFlashAttribute("configuracion", configuracion);
        }

        return REDIRECT_CONFIG;
    }

    @GetMapping("/verActiva")
    public String verActiva(Model model) {
        try {
            ConfiguracionPromocionDTO configuracion = servicio.obtenerConfiguracionActiva();
            model.addAttribute("configuracion", configuracion);
        } catch (Exception e) {
            model.addAttribute("msgError", e.getMessage());
        }
        return VIEW_ACTIVA;
    }

    @GetMapping("/envioManual")
    public String envioManual() {
        return VIEW_ENVIO;
    }

    @PostMapping("/envioManual")
    public String enviarManual(RedirectAttributes redirectAttributes) {
        try {
            String respuesta = servicio.enviarPromocionesManual();
            redirectAttributes.addFlashAttribute("msgExito",
                    respuesta != null ? respuesta : "Promociones enviadas correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msgError", e.getMessage());
        }

        return "redirect:/promociones/envioManual";
    }
}
