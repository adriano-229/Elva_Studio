package com.example.demo.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Medico;
import com.example.demo.repositories.BaseRepository;
import com.example.demo.repositories.MedicoRepository;
import com.example.demo.services.MedicoService;

@Service
public class MedicoServiceImp extends BaseServiceImp<Medico, Long> implements MedicoService{
	
	@Autowired
	private MedicoRepository medicoRepo;
	
	public MedicoServiceImp(BaseRepository<Medico, Long> baseRepository) {
		super(baseRepository);
	}



}
