package com.example.biblioteca.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

	@GetMapping("/")
	public String inicio() {
		return "view/inicio";
	}
}