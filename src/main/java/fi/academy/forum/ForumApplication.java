package fi.academy.forum;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}

	@Bean
	CommandLineRunner luoKayttaja(KayttajaRepositorio krepo, ViestiRepositorio vrepo) {
		return (args -> {
			Kayttaja k1 = new Kayttaja("admin", "admin");
			krepo.save(k1);
			Viesti v1 = new Viesti("terve", k1);
			vrepo.save(v1);
		});
	}
}
