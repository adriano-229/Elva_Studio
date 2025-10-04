package com.elva.tp1.service;

import com.elva.tp1.domain.Persona;
import com.elva.tp1.repository.PersonaRepository;

public class PersonaService extends BaseService<Persona, Long> {

    public PersonaService(PersonaRepository repository) {
        super(repository);
    }


}