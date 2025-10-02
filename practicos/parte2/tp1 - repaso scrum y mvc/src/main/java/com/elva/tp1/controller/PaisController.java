package com.elva.tp1.controller;

import com.elva.tp1.entity.Pais;
import com.elva.tp1.service.PaisService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pais")
public class PaisController extends GenericController<Pais, Integer> {

    public PaisController(PaisService service) {
        super(service, "/pais", "pais");
    }

    @Override
    protected String getEntityClassName() {
        return "com.example.app.domain.Pais";
    }
}
