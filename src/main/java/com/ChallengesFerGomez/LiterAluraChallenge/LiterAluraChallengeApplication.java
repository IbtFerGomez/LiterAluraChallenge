package com.ChallengesFerGomez.LiterAluraChallenge;

import com.ChallengesFerGomez.LiterAluraChallenge.Principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraChallengeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Inicializa la clase Principal y muestra el menú
		Principal principal = new Principal();
		principal.muestraElMenu();
	}
}
