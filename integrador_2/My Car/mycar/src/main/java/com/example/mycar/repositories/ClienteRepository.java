package com.example.mycar.repositories;

import com.example.mycar.entities.Cliente;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends BaseRepository<Cliente, Long> {
}

