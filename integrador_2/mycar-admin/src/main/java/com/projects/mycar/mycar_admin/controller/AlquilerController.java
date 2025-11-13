package com.projects.mycar.mycar_admin.controller;

import com.projects.mycar.mycar_admin.domain.*;
import com.projects.mycar.mycar_admin.service.impl.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/alquiler")
@Getter
public class AlquilerController extends BaseControllerImpl<AlquilerDTO, AlquilerServiceImpl> {

    @Autowired
    private VehiculoServiceImpl vehiculoService;

    @Autowired
    private ClienteServiceImpl clienteService;

    @Autowired
    private CodigoDescuentoServiceImpl codigoDescuentoService;

    @Autowired
    private CostoServiceImpl costoService;

    private String viewList = "view/alquiler/listar";
    private String viewEdit = "view/alquiler/eAlquiler";
    private String redirectList = "redirect:/alquiler/listar";

    @Override
    @GetMapping("/crear")
    public String crear(Model model) {

        return null;
    }

    @PostMapping("/crearConCotizacion")
    public String crearAlquilerConCotizacion(@ModelAttribute AlquilerFormDTO alquilerCotizado,
                                             Model model) {

        try {


            AlquilerDTO alquiler = new AlquilerDTO();
            alquiler.setFechaDesde(alquilerCotizado.getFechaDesde());
            alquiler.setFechaHasta(alquilerCotizado.getFechaHasta());
            alquiler.setCantidadDias(alquilerCotizado.getCantidadDias());
            alquiler.setCostoCalculado(alquilerCotizado.getTotalConDescuento());
            alquiler.setVehiculoId(alquilerCotizado.getIdVehiculo());
            alquiler.setClienteId(alquilerCotizado.getIdCliente());

            VehiculoDTO vehiculo = vehiculoService.findById(alquilerCotizado.getIdVehiculo());
            model.addAttribute("vehiculoSeleccionado", vehiculo);

            ClienteDTO cliente = clienteService.findById(alquiler.getClienteId());
            model.addAttribute("cliente", cliente);

            DocumentacionDTO documentacion = new DocumentacionDTO();
            alquiler.setDocumentacion(documentacion);
            model.addAttribute("alquilerCotizado", alquilerCotizado);
            model.addAttribute("alquiler", alquiler);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msgError", "error al crear alquiler con cotizacion");
        }

        return viewEdit;
    }

    @GetMapping("/cotizar")
    public String cotizar(Model model) {

        try {

            List<VehiculoDTO> vehiculos = vehiculoService.findAll();
            AlquilerFormDTO alquilerCotizar = new AlquilerFormDTO();
            model.addAttribute("alquiler", alquilerCotizar);
            model.addAttribute("listaVehiculos", vehiculos);

            model.addAttribute("fechasDisponibles", List.of("2025-11-12", "2025-11-15", "2025-11-18"));


        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msgError", "error al cargar pag de cotizaciones");
        }

        return "view/alquiler/cotizar";
    }

    @PostMapping("/cotizar")
    public String cotizar(@ModelAttribute AlquilerFormDTO alquilerCotizar,
                          Model model) {

        try {

            if (alquilerCotizar.getCodigoDescuento() != null && !alquilerCotizar.getCodigoDescuento().isBlank()) {
                if (!codigoDescuentoService.existePorCodigo(alquilerCotizar.getCodigoDescuento())) {
                    model.addAttribute("msgError", "El codigo de descuento no es válido");
                } else {
                    model.addAttribute("codigoDescuento", alquilerCotizar.getCodigoDescuento());
                }
            }

            model.addAttribute("alquiler", alquilerCotizar);


            List<VehiculoDTO> vehiculos = vehiculoService.findAll();
            model.addAttribute("listaVehiculos", vehiculos);

            List<ClienteDTO> clientes = clienteService.findAll();
            model.addAttribute("listaClientes", clientes);

            model.addAttribute("fechasDisponibles", List.of("2025-11-12", "2025-11-15", "2025-11-18"));

            if (alquilerCotizar.getFechaDesde() == null
                    || alquilerCotizar.getFechaHasta() == null
                    || alquilerCotizar.getFechaDesde().toString().isBlank()
                    || alquilerCotizar.getFechaHasta().toString().isBlank()) {
                model.addAttribute("msgError", "Debe seleccionar al menos una fecha válida.");
                return "view/alquiler/cotizar";
            }

            AlquilerFormDTO alquilerCotizado = costoService.cotizarAlquiler(alquilerCotizar);
            model.addAttribute("alquilerCotizado", alquilerCotizado);

            VehiculoDTO vehiculo = vehiculoService.findById(alquilerCotizado.getIdVehiculo());
            model.addAttribute("vehiculoSeleccionado", vehiculo);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msgError", "error al cotizar");
        }

        return "view/alquiler/cotizar";
    }

    @GetMapping("/detalle/{id}")
    public String verDetalleLibro(@PathVariable Long id, Model model) {
        try {
            AlquilerDTO alquiler = servicio.findById(id);
            System.out.println("cliente: " + alquiler.getCliente());
            model.addAttribute("alquiler", alquiler);

        } catch (Exception e) {
            model.addAttribute("msgError", "No se pudo cargar el detalle del libro");
            e.printStackTrace();

        }
        return "view/alquiler/detalle";
    }

}
