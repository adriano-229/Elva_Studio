package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entities.Paciente;
import com.example.demo.serviceImp.PacienteServiceImp;
import com.example.demo.template.ConsultaPediatria;



@Controller
@RequestMapping("/pacientes")
public class PacienteController extends BaseControllerImp<Paciente, PacienteServiceImp> {

    @Autowired
    private PacienteServiceImp pacienteService;

	@Override
	protected String getListView() {
		return "pacientes";
	}

	@Override
	protected String getFormView() {
		return "paciente_form";
	}

	@Override
	protected String getRedirectToList() {
		return "redirect:/pacientes";
	}

	@Override
	protected Paciente createNewEntity() {
		return new Paciente();
	}
	
	@GetMapping("/{id}")
    public String verHistoriaClinica(@PathVariable Long id, ModelMap model) throws Exception {
        Paciente paciente = pacienteService.findByid(id);
        model.addAttribute("paciente", paciente);
        return "historia_clinica";
    }
	
	@GetMapping("/{id}/nueva-consulta")
    public String nuevaConsulta(@PathVariable Long id, ModelMap model) throws Exception {
        ConsultaPediatria consulta = new ConsultaPediatria();
        consulta.setPaciente(pacienteService.findByid(id));
        model.addAttribute("consulta", consulta);
        return "consulta_form";
    }
    
    

    /*

    @GetMapping("/editar/{id}")
    public String editarPaciente(@PathVariable Long id, ModelMap model) throws Exception {
        model.addAttribute("paciente", pacienteService.findByid(id));
        return "paciente_form";
    }
    
    @PostMapping("/{id}/guardar-consulta")
    public String guardarConsulta(@PathVariable Long id, @ModelAttribute ConsultaPediatria consulta) {
        pacienteService.agregarConsulta(id, consulta);
        return "redirect:/pacientes/" + id;
    }
    
    @GetMapping
    public String listarPacientes(ModelMap model) throws Exception {
        model.addAttribute("pacientes", pacienteService.findAll());
        System.out.println("ESTOY EN PACIENTES");
        return "pacientes";
    }

    @GetMapping("/nuevo")
    public String nuevoPaciente(ModelMap model) {
        model.addAttribute("paciente", new Paciente());
        return "paciente_form";
    }

    @PostMapping("/guardar")
    public String guardarPaciente(@ModelAttribute Paciente paciente) throws Exception {
        pacienteService.save(paciente);
        return "redirect:/pacientes";
    }

    

    @GetMapping("/eliminar/{id}")
    public String eliminarPaciente(@PathVariable Long id) throws Exception {
        pacienteService.delete(id);
        return "redirect:/pacientes";
    }

    

   */
}
