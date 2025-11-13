package com.example.ApiExterna.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ApiExterna.dao.TeamDao;
import com.example.ApiExterna.domain.dto.Team;

@Service
public class TeamService {
	
	@Autowired
	private TeamDao dao;
	
	public List<Team> listarPorLiga(String nombreLiga) throws Exception{
		
		try {
			
			return dao.buscarPorLiga(nombreLiga);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	public Team buscarPorId(String nombreLiga, String idEquipo) throws Exception{
		
		List<Team> equipos = listarPorLiga(nombreLiga);
	    
	    for (Team t : equipos) {
	        if (t.getIdTeam().equals(idEquipo)) {
	            return t;
	        }
	    }

	    // Si no se encontró, devolvés null o lanzás excepción según prefieras:
	    throw new Exception("Equipo con ID " + idEquipo + " no encontrado en liga " + nombreLiga);
	}

}
