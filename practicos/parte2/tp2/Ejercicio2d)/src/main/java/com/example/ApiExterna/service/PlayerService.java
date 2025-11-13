package com.example.ApiExterna.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ApiExterna.dao.PlayerDao;
import com.example.ApiExterna.domain.dto.Player;

@Service
public class PlayerService {
	
	@Autowired
	private PlayerDao dao;
	
	public List<Player> listarPorEquipo(String idEquipo) throws Exception{
		
		try {
			
			return dao.buscarPorEquipo(idEquipo);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
	}
	
	public Player buscarPorId(String idEquipo, String idPlayer) throws Exception{
		
		List<Player> jugadores = listarPorEquipo(idEquipo);
	    
	    for (Player p : jugadores) {
	        if (p.getIdPlayer().equals(idPlayer)) {
	            return p;
	        }
	    }

	    // Si no se encontró, devolvés null o lanzás excepción según prefieras:
	    throw new Exception("Jugador con ID " + idPlayer + " no encontrado en equipo " + idEquipo);
	}
}
