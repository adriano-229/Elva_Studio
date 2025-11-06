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

import com.example.ApiExterna.domain.dto.Player;
import com.example.ApiExterna.domain.dto.ligaEquipoJugador;
import com.example.ApiExterna.service.PlayerService;

//HACER VER DETALLE
@Controller
@RequestMapping("/player")
public class PlayerController {
	
	@Autowired
	private PlayerService service;
	
	private String viewList = "view/player/lPlayer";
	
	@GetMapping("")
	public String listar(@ModelAttribute ligaEquipoJugador wrapper, Model model, RedirectAttributes redirectAttributes) {
		
		
		try {
			List<Player> players = service.listarPorEquipo(wrapper.getIdTeam()); //wrapper.getIdTeam()
			
			model.addAttribute("wrapper", wrapper);
			model.addAttribute("players", players);
			
			System.out.println("JUGADORES: " + players);
		
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("msgError", "Error al listar los jugadores");
			redirectAttributes.addFlashAttribute("wrapper", wrapper);
			return "redirect:/team/detalle/" + wrapper.getIdTeam();
		}
		
		return viewList;
	}
	
	@GetMapping("/detalle/{idPlayer}")
	public String verDetalle(@PathVariable String idPlayer, @ModelAttribute ligaEquipoJugador wrapper, Model model, RedirectAttributes redirectAttributes) {
		
		try {
			Player player = service.buscarPorId(wrapper.getIdTeam(), idPlayer); //wrapper.getIdTeam(), idPlayer
			
			model.addAttribute("player", player);
			model.addAttribute("wrapper", wrapper);
			
			System.out.println("ESTOY EN DETALLE PLAYER");
			System.out.println("PLAYERS: " + player);
			
			return "view/player/detalle";
		
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("wrapper", wrapper);
			redirectAttributes.addFlashAttribute("msgError", "Error al buscar detalle de jugador");
			
			return "redirect:/team/detalle/" + wrapper.getIdTeam();
		}
		
		
	}
	
	

}
