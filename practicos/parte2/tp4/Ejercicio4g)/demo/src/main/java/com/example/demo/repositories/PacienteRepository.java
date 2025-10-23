package com.example.demo.repositories;

import org.springframework.stereotype.Repository;

import com.example.demo.entities.Paciente;

@Repository
public interface PacienteRepository extends BaseRepository<Paciente, Long> {

}
