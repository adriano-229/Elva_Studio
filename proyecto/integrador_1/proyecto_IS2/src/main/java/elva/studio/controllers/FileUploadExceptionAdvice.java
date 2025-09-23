package elva.studio.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import elva.studio.dto.DeudaForm;
import elva.studio.entities.Socio;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(HttpSession session, Model model) {
        model.addAttribute("msgError", "Archivo demasiado grande. Máximo permitido: 10MB");
        
        DeudaForm deudaForm = (DeudaForm) session.getAttribute("deudaForm");
        deudaForm.setComprobante(null);
        Socio socio = (Socio) session.getAttribute("socio");

        model.addAttribute("deudaForm", deudaForm);
        model.addAttribute("socio", socio);

        return "transferencia"; // muestra la misma página directamente
    }

    /*public String handleMaxSizeException(MaxUploadSizeExceededException exc, HttpSession session, Model model) {
        
    		model.addAttribute("msgError", "Archivo demasiado grande. Máximo permitido: 10MB");
        DeudaForm deudaForm = (DeudaForm) session.getAttribute("deudaForm");
        Socio socio = (Socio) session.getAttribute("socio");
        System.out.println("ESTOY EN CPNTROLLER - DEUDAFORM: idSocio: " + deudaForm.getIdSocio());
        
        model.addAttribute("socio", socio);
        model.addAttribute("deudaForm", deudaForm);
        
        return "redirect:/pagos/transferencia";
    }*/
}
