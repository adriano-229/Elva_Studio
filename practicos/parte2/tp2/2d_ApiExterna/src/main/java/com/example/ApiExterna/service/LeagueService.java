package com.example.ApiExterna.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ApiExterna.dao.LeagueDao;
import com.example.ApiExterna.domain.dto.League;

@Service
public class LeagueService {
	
	@Autowired
	private LeagueDao dao;
	
	public List<League> findAll() throws Exception{
		
		try {
			
			List<League> ligas = dao.listar();
			return ligas;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	public League findById(String id) throws Exception{
		
		try {
			
			return dao.buscarPorId(id);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new Exception("Error al buscar la liga", e);
			
		}
	}

}
