package com.projects.gym.gym_app.controller;

import com.projects.gym.gym_app.domain.FormaDePago;
import com.projects.gym.gym_app.domain.enums.EstadoFactura;
import com.projects.gym.gym_app.domain.enums.TipoPago;
import com.projects.gym.gym_app.repository.FormaDePagoRepository;
import com.projects.gym.gym_app.repository.SocioRepository;
import com.projects.gym.gym_app.service.FacturaService;
import com.projects.gym.gym_app.service.dto.EmisionFacturaCommand;
import com.projects.gym.gym_app.service.dto.FacturaDTO;
import com.projects.gym.gym_app.service.dto.PagoCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/facturas")
@RequiredArgsConstructor
public class FacturaAdminController {

    private final FacturaService facturaService;
    private final SocioRepository socioRepository;
    private final FormaDePagoRepository formaDePagoRepository;

    @GetMapping
    public String listar(@RequestParam(required = false) Long socioId,
                         @RequestParam(required = false) EstadoFactura estado,
                         @RequestParam(required = false) String desde,
                         @RequestParam(required = false) String hasta,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "10") int size,
                         Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "fechaFactura"));
        Page<FacturaDTO> facturas = facturaService.listar(
                socioId != null ? socioId.toString() : null,
                estado != null ? estado.name() : null,
                desde,
                hasta,
                pageable);

        model.addAttribute("facturas", facturas);
        model.addAttribute("socios", socioRepository.findByEliminadoFalseOrderByApellidoAscNombreAsc());
        model.addAttribute("socioId", socioId);
        model.addAttribute("estado", estado != null ? estado.name() : null);
        model.addAttribute("desde", desde);
        model.addAttribute("hasta", hasta);
        model.addAttribute("titulo", "Facturas");
        model.addAttribute("active", "facturas");
        return "factura/list";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        if (!model.containsAttribute("form")) {
            model.addAttribute("form", new EmisionFacturaCommand());
        }
        loadFormOptions(model);
        model.addAttribute("titulo", "Nueva factura");
        model.addAttribute("active", "facturas");
        return "factura/form";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute("form") EmisionFacturaCommand form,
                        BindingResult bindingResult,
                        @RequestParam(name = "cuotasIds", required = false) String cuotasIdsRaw,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        form.setCuotasIds(parseCuotas(cuotasIdsRaw));

        if (form.getCuotasIds() == null || form.getCuotasIds().isEmpty()) {
            bindingResult.rejectValue("cuotasIds", "cuotasIds", "Debe seleccionar al menos una cuota");
        }
        if (form.isMarcarPagada() && (form.getFormaDePagoId() == null || form.getFormaDePagoId().isBlank())) {
            bindingResult.rejectValue("formaDePagoId", "formaDePagoId", "Seleccione la forma de pago");
        }

        if (bindingResult.hasErrors()) {
            loadFormOptions(model);
            model.addAttribute("titulo", "Nueva factura");
            model.addAttribute("active", "facturas");
            return "factura/form";
        }
        try {
            FacturaDTO factura = facturaService.crearFactura(form);
            redirectAttributes.addFlashAttribute("mensaje", "Factura generada correctamente");
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
            return "redirect:/admin/facturas/" + factura.id();
        } catch (RuntimeException ex) {
            bindingResult.reject("factura", ex.getMessage());
            loadFormOptions(model);
            model.addAttribute("titulo", "Nueva factura");
            model.addAttribute("active", "facturas");
            return "factura/form";
        }
    }

    @GetMapping("/{id}")
    public String ver(@PathVariable String id,
                      Model model,
                      RedirectAttributes redirectAttributes,
                      @RequestParam(required = false) String mensaje,
                      @RequestParam(required = false) String mensajeTipo) {
        try {
            FacturaDTO factura = facturaService.buscarPorId(id);
            Map<String, Object> atributos = model.asMap();
            Object pagoExistente = atributos.get("pagoForm");
            if (pagoExistente == null) {
                model.addAttribute("pagoForm", new PagoCommand());
            }
            List<FormaDePago> opciones = formasPagoManuales();
            boolean sinFormasPago = opciones.isEmpty();
            model.addAttribute("formasPago", opciones);
            model.addAttribute("sinFormasPago", sinFormasPago);
            model.addAttribute("factura", factura);
            if (mensaje != null && !mensaje.isBlank() && !atributos.containsKey("mensaje")) {
                model.addAttribute("mensaje", mensaje);
            }
            if (mensajeTipo != null && !mensajeTipo.isBlank() && !atributos.containsKey("mensajeTipo")) {
                model.addAttribute("mensajeTipo", mensajeTipo);
            }
            model.addAttribute("titulo", "Factura " + factura.numeroFactura());
            model.addAttribute("active", "facturas");
            return "factura/view";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("mensaje", ex.getMessage() != null ? ex.getMessage() : "No se encontró la factura solicitada");
            redirectAttributes.addFlashAttribute("mensajeTipo", "warning");
            return "redirect:/admin/facturas";
        }
    }

    @PostMapping("/{id}/confirmar")
    public String confirmarPago(@PathVariable String id,
                                @ModelAttribute("pagoForm") PagoCommand pagoForm,
                                RedirectAttributes redirectAttributes) {
        try {
            facturaService.confirmarPagoManual(id, pagoForm);
            redirectAttributes.addFlashAttribute("mensaje", "Pago confirmado correctamente");
            redirectAttributes.addFlashAttribute("mensajeTipo", "success");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("mensaje", ex.getMessage());
            redirectAttributes.addFlashAttribute("mensajeTipo", "danger");
        }
        return "redirect:/admin/facturas/" + id;
    }

    @PostMapping("/{id}/anular")
    public String anular(@PathVariable String id,
                         @RequestParam(required = false) String motivo,
                         RedirectAttributes redirectAttributes) {
        try {
            facturaService.anularFactura(id, motivo);
            redirectAttributes.addFlashAttribute("mensaje", "Factura anulada");
            redirectAttributes.addFlashAttribute("mensajeTipo", "info");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("mensaje", ex.getMessage());
            redirectAttributes.addFlashAttribute("mensajeTipo", "danger");
        }
        return "redirect:/admin/facturas/" + id;
    }

    private void loadFormOptions(Model model) {
        model.addAttribute("socios", socioRepository.findByEliminadoFalseOrderByApellidoAscNombreAsc());
        model.addAttribute("formasPago", formasPagoManuales());
    }

    private List<FormaDePago> formasPagoManuales() {
        return formaDePagoRepository.findByEliminadoFalseOrderByTipoPagoAsc().stream()
                .filter(fp -> fp.getTipoPago() == TipoPago.EFECTIVO || fp.getTipoPago() == TipoPago.TRANSFERENCIA)
                .toList();
    }

    private List<String> parseCuotas(String cuotasIdsRaw) {
        if (cuotasIdsRaw == null || cuotasIdsRaw.isBlank()) {
            return List.of();
        }
        return List.of(cuotasIdsRaw.split(","))
                .stream()
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();
    }
}
