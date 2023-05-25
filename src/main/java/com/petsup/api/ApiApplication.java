package com.petsup.api;

import com.petsup.api.service.UsuarioService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Timer;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);

		Timer timer = new Timer();
		UsuarioService usuarioService = new UsuarioService();

		timer.schedule(usuarioService.gravarAvaliacoes(), 0, 86400000);
	}

}
