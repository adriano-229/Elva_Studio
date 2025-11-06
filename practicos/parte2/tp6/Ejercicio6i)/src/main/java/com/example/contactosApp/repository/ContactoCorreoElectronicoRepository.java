package com.example.contactosApp.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.contactosApp.domain.ContactoCorreoElectronico;

@Repository
public interface ContactoCorreoElectronicoRepository extends ContactoRepository<ContactoCorreoElectronico>{
	
	Optional<ContactoCorreoElectronico> findByIdAndActivoTrue(Long id);
}
