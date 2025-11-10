package com.example.mycar.repositories;

import com.example.mycar.entities.Contacto;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoRepository extends BaseRepository<Contacto, Long> {
}

