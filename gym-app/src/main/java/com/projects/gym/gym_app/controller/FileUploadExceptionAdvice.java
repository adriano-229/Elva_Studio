package com.projects.gym.gym_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.projects.gym.gym_app.domain.Socio;
import com.projects.gym.gym_app.domain.Usuario;
import com.projects.gym.gym_app.repository.SocioRepository;
import com.projects.gym.gym_app.service.dto.DeudaForm;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class FileUploadExceptionAdvice {
	
	@Autowired
	private SocioRepository repoSocio;

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(Authentication authentication, HttpSession session, Model model) {
        model.addAttribute("msgError", "Archivo demasiado grande. Máximo permitido: 10MB");
        
        DeudaForm deudaForm = (DeudaForm) session.getAttribute("deudaForm");
        deudaForm.setComprobante(null);
        
        Usuario usuario = (Usuario) authentication.getDetails();

        Socio socio = this.repoSocio
                         .findByUsuario_NombreUsuarioAndEliminadoFalse(authentication.getName())
                         .orElseThrow(() -> new RuntimeException("El usuario no tiene un socio asociado"));


        model.addAttribute("deudaForm", deudaForm);
        model.addAttribute("socio", socio);

        return "pago/transferencia"; // muestra la misma página directamente
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