package com.projects.mycar.mycar_admin.controller;

import com.projects.mycar.mycar_admin.domain.ClienteDTO;
import com.projects.mycar.mycar_admin.domain.DireccionDTO;
import com.projects.mycar.mycar_admin.domain.enums.TipoDocumento;
import com.projects.mycar.mycar_admin.service.impl.ClienteServiceImpl;
import com.projects.mycar.mycar_admin.service.impl.LocalidadServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clientes")
public class ClienteController extends BaseControllerImpl<ClienteDTO, ClienteServiceImpl> {

    private static final String VIEW_LIST = "view/cliente/listar";
    private static final String VIEW_FORM = "view/cliente/form";
    private static final String VIEW_DETAIL = "view/cliente/detalle";
    private static final String REDIRECT_LIST = "redirect:/clientes/listar";

    private final LocalidadServiceImpl localidadService;

    public ClienteController(LocalidadServiceImpl localidadService) {
        this.localidadService = localidadService;
    }

    @Override
    protected String getViewList() {
        return VIEW_LIST;
    }

    @Override
    protected String getViewEdit() {
        return VIEW_FORM;
    }

    @Override
    protected String getRedirectList() {
        return REDIRECT_LIST;
    }

    @Override
    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("msgError", "La creación manual de clientes no está disponible.");
        return REDIRECT_LIST;
    }

    @Override
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, Model model) {
        try {
            ClienteDTO cliente = servicio.findById(id);
            prepararFormulario(model, cliente, false);
        } catch (Exception e) {
            model.addAttribute("msgError", "No se pudo cargar el cliente solicitado");
            return VIEW_LIST;
        }
        return VIEW_FORM;
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        try {
            ClienteDTO cliente = servicio.findById(id);
            model.addAttribute("cliente", cliente);
        } catch (Exception e) {
            model.addAttribute("msgError", "No se pudo cargar el detalle del cliente");
        }
        return VIEW_DETAIL;
    }

    private void prepararFormulario(Model model, ClienteDTO cliente, boolean isNew) {
        if (cliente.getDireccion() == null) {
            cliente.setDireccion(new DireccionDTO());
        }
        model.addAttribute("entity", cliente);
        model.addAttribute("isNew", isNew);
        try {
            model.addAttribute("listaLocalidades", localidadService.findAll());
        } catch (Exception e) {
            model.addAttribute("listaLocalidades", java.util.List.of());
            model.addAttribute("msgError", "No se pudieron cargar las localidades");
        }
        model.addAttribute("tiposDocumento", TipoDocumento.values());
    }

    private void copiarDatos(ClienteDTO destino, ClienteDTO origen) {
        destino.setNombre(origen.getNombre());
        destino.setApellido(origen.getApellido());
        destino.setTipoDocumento(origen.getTipoDocumento());
        destino.setNumeroDocumento(origen.getNumeroDocumento());
        destino.setDireccionEstadia(origen.getDireccionEstadia());

        if (destino.getDireccion() == null) {
            destino.setDireccion(new DireccionDTO());
        }
        if (origen.getDireccion() != null) {
            destino.getDireccion().setCalle(origen.getDireccion().getCalle());
            destino.getDireccion().setNumeracion(origen.getDireccion().getNumeracion());
            destino.getDireccion().setBarrio(origen.getDireccion().getBarrio());
            destino.getDireccion().setReferencia(origen.getDireccion().getReferencia());
            destino.getDireccion().setLocalidadId(origen.getDireccion().getLocalidadId());
        }
    }
}
