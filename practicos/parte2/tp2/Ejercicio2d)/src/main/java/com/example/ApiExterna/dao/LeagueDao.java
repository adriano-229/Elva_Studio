package com.example.ApiExterna.dao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.example.ApiExterna.domain.dto.League;
import com.example.ApiExterna.domain.dto.LeagueResponse;

@Repository
public class LeagueDao {
	
	private String baseUrl = "https://www.thesportsdb.com/api/v1/json/123";
	
	@Autowired
	private RestTemplate restTemplate;
	
	public List<League> listar() throws Exception {
		
		try {
			String uri = baseUrl + "/all_leagues.php";
			// response = {"leagues": [{"idLeague": "4328", "strLeague": "English Premier League", "strSport": "Soccer"},..]}
		
			ResponseEntity<LeagueResponse> response = restTemplate.getForEntity(uri, LeagueResponse.class);
			return response.getBody().getLeagues();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al listar ligas", e);
		}
		
	}
	
	public League buscarPorId(String id) throws Exception{
		
		try {
			
			String uri = baseUrl + "/lookupleague.php?id=" + id;
			ResponseEntity<LeagueResponse> response =
	                restTemplate.getForEntity(uri, LeagueResponse.class);

	        List<League> lista = response.getBody().getLeagues();
	        if (lista != null && !lista.isEmpty()) {
	            return lista.get(0);
	            
	        } else {
	            throw new Exception("Liga no encontrada");
	        }
	    	
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar la liga");
		}
	}
	
}
