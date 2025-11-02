package com.example.ApiExterna.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.example.ApiExterna.domain.dto.Player;
import com.example.ApiExterna.domain.dto.PlayerResponse;

@Repository
public class PlayerDao {
	
	private String baseUrl = "https://www.thesportsdb.com/api/v1/json/123";
	
	@Autowired
	private RestTemplate restTemplate;
	
	public List<Player> buscarPorEquipo(String idEquipo) throws Exception{
		
		try {
			
			String uri = baseUrl + "/lookup_all_players.php?id=" + idEquipo;
			ResponseEntity<PlayerResponse> response = restTemplate.getForEntity(uri, PlayerResponse.class);
			return response.getBody().getPlayer();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error a buscar jugadores por equipo");
		}
	}
	
	

}
