package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.serviceImp.HistoriaClinicaServiceImp;

@Controller
@RequestMapping("/historia-clinica")
public class HistoriaClinicaController {

    @Autowired
    private HistoriaClinicaServiceImp hcservice;

    @GetMapping
    public String listarHistoriasClinicas(ModelMap model) throws Exception {
        model.addAttribute("historias", hcservice.findAll());
        System.out.println("ESTOY EN historia clinica");
        return "historia_clinica";
    }
}