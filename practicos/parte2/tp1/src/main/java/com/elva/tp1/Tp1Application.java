package com.elva.tp1;

import com.elva.tp1.config.FlywayConfig;
import com.elva.tp1.domain.Empresa;
import com.elva.tp1.service.EmpresaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Tp1Application {

    // Java
    public static void main(String[] args) {
       SpringApplication.run(Tp1Application.class, args);

    }
}
