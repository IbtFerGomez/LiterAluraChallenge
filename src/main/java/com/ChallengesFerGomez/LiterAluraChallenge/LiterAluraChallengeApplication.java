package com.ChallengesFerGomez.LiterAluraChallenge;

import com.ChallengesFerGomez.LiterAluraChallenge.principal.LiterAluraPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraChallengeApplication implements CommandLineRunner {

	private final LiterAluraPrincipal principal;

	@Autowired
	public LiterAluraChallengeApplication(LiterAluraPrincipal principal) {
		this.principal = principal;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) {
		principal.muestraElMenu();
	}
}
