package com.example.demo.repositories;

import org.springframework.stereotype.Repository;

import com.example.demo.entities.Medico;

@Repository
public interface MedicoRepository extends BaseRepository<Medico, Long> {

}
