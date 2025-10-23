package com.example.demo.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Usuario;
import com.example.demo.repositories.BaseRepository;
import com.example.demo.repositories.UsuarioRepository;
import com.example.demo.services.UsuarioService;

@Service
public class UsuarioServiceImp extends BaseServiceImp<Usuario, Long> implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepo;
	
	public UsuarioServiceImp(BaseRepository<Usuario, Long> baseRepository) {
		super(baseRepository);
	}


}
