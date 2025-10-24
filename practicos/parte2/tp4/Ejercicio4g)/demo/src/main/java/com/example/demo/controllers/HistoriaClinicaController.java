package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entities.HistoriaClinica;
import com.example.demo.serviceImp.HistoriaClinicaServiceImp;

@Controller
@RequestMapping("/historia-clinica")
public class HistoriaClinicaController extends BaseControllerImp<HistoriaClinica, HistoriaClinicaServiceImp> {

	public HistoriaClinicaController(HistoriaClinicaServiceImp hcservice) {
        this.baseService = hcservice;
    }

	@Override
	protected String getListView() {
		return "historia_clinica";
	}

	@Override
	protected String getRedirectToList() {
		return "redirect:/historia-clinica";
	}

	@Override
	protected HistoriaClinica createNewEntity() {
		return new HistoriaClinica();
	}

	@Override
	protected String getFormView() {
		// TODO Auto-generated method stub
		return null;
	}

	
}