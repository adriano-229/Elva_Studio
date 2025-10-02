package com.elva.tp1.service.impl;

import com.elva.tp1.domain.Persona;
import com.elva.tp1.repository.PersonaRepository;
import com.elva.tp1.service.PersonaService;
import org.springframework.stereotype.Service;

@Service
public class PersonaServiceImpl extends CrudServiceImpl<Persona, Long> implements PersonaService {

    public PersonaServiceImpl(PersonaRepository repository) {
        super(repository);
    }
}
