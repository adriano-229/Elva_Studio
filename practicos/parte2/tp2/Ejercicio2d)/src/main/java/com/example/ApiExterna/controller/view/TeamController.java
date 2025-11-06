package com.example.ApiExterna.controller.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ApiExterna.domain.dto.Team;
import com.example.ApiExterna.domain.dto.ligaEquipoJugador;
import com.example.ApiExterna.service.TeamService;

@Controller
@RequestMapping("/team")
public class TeamController {
	
	@Autowired
	private TeamService service;
	
	private String viewList = "view/team/lTeam";
	
	@GetMapping("")
	public String listar(@ModelAttribute ligaEquipoJugador wrapper, Model model, RedirectAttributes redirectAttributes) {
		
		try {
			List<Team> equipos = service.listarPorLiga(wrapper.getLeague()); //wrapper.getLeague()
			
			model.addAttribute("wrapper", wrapper);
			model.addAttribute("teams", equipos);
			
			System.out.println("EQUIPOS: " + equipos);
		
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msgError", "Error al listar los equipos");
			redirectAttributes.addFlashAttribute("wrapper", wrapper);
			return "redirect:/league/detalle/" + wrapper.getIdLeague();
		}
		
		return viewList;
	}
	
	@GetMapping("/detalle/{idTeam}")
	public String verDetalle(@PathVariable String idTeam, @ModelAttribute ligaEquipoJugador wrapper, Model model, RedirectAttributes redirectAttributes) {
		
		try {
			Team team = service.buscarPorId(wrapper.getLeague(), idTeam); //wrapper.getLeague(), idTeam
			
			wrapper.setIdTeam(idTeam);
			
			model.addAttribute("team", team);
			model.addAttribute("wrapper", wrapper);
			
			System.out.println("ESTOY EN DETALLE EQUIPO");
			System.out.println("EQUIPO: " + team);
		
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("wrapper", wrapper);
			redirectAttributes.addFlashAttribute("msgError", "Error al buscar detalle de equipo");
			return "redirect:/league/detalle/" + wrapper.getIdLeague();
		}
		
		return "view/team/detalle";
	}
	
	/* SI ME DA CHANCE LO HAGO
	@GetMapping("/estadio")
	public String verEstadio(@ModelAttribute ligaEquipoJugador wrapper) {
		
		
		
	}*/

}
