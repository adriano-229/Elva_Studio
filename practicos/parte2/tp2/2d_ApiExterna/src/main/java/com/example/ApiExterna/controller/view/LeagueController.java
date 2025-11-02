package com.example.ApiExterna.controller.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ApiExterna.domain.dto.League;
import com.example.ApiExterna.domain.dto.ligaEquipoJugador;
import com.example.ApiExterna.service.LeagueService;

@Controller
@RequestMapping("/league")
public class LeagueController {
	
	@Autowired
	private LeagueService service;
	
	private String viewList = "view/league/lLeague";
	private String redirectHome = "redirect:/view/incio";
	
	@GetMapping("")
	public String listar(Model model, RedirectAttributes redirectAttributes) {
		
		try {
			List<League> leagues = service.findAll();
			System.out.println("LIGAS: " + leagues);
			
			model.addAttribute("leagues", leagues);
		
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msgError", "Error al listar las ligas");
			return redirectHome;
		}
		
		return viewList;
	}
	
	@GetMapping("/detalle/{idLeague}")
	public String verDetalle(@PathVariable String idLeague, Model model, RedirectAttributes redirectAttributes) {
		
		try {
			
			System.out.println("ESTOY EN DETALLE LIGA");
			
			
			League league = service.findById(idLeague);
			
			System.out.println("LIGA: " + league);
			
			ligaEquipoJugador wrapper = new ligaEquipoJugador();
			wrapper.setIdLeague(idLeague);
			wrapper.setLeague(league.getStrLeague());
			
			model.addAttribute("league", league);
			model.addAttribute("wrapper", wrapper);
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msgError", "Error de sistema");
			
			return "redirect:/league";
		}
		
		return "view/league/detalle";
	}
	
	

}
