package com.example.ApiExterna.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.example.ApiExterna.domain.dto.Team;
import com.example.ApiExterna.domain.dto.TeamResponse;

@Repository
public class TeamDao {
	
	private String baseUrl = "https://www.thesportsdb.com/api/v1/json/123";
	
	@Autowired
	private RestTemplate restTemplate;
	
	public List<Team> buscarPorLiga(String nombreLiga) throws Exception{
		
		try {
			
			// sustituimos los espacios del nombre por guion bajo
			String liga = nombreLiga.trim().replace(" ", "_");
			System.out.println("NOMBRE LIGA: " + liga);
			String uri = baseUrl + "/search_all_teams.php?l=" + liga;

			ResponseEntity<TeamResponse> response = restTemplate.getForEntity(uri, TeamResponse.class);
			return response.getBody().getTeams();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar equipos por liga");
			
		}
		
	}

}
