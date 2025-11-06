package com.example.contactosApp.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.contactosApp.domain.ContactoTelefonico;
import com.example.contactosApp.domain.enums.TipoTelefono;

@Repository
public interface ContactoTelefonicoRepository extends ContactoRepository<ContactoTelefonico>{
	
	
	List<ContactoTelefonico> findByActivoTrueAndTipoTelefono(TipoTelefono tipo) throws Exception;

}
