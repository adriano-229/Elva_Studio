package com.projects.mycar.mycar_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyCarServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyCarServerApplication.class, args);
    }

}
