package com.elva.videogames;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.elva.videogames")
public class VideogamesApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideogamesApplication.class, args);
    }
}