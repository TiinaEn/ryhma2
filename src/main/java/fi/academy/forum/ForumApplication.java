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
			Kayttaja admin = new Kayttaja("admin", "admin");
			krepo.save(admin);
			Kayttaja user = new Kayttaja("user", "user");
			krepo.save(user);
			Viesti v1 = new Viesti("terve", admin);
			Viesti v2 = new Viesti("Halloota!", user);
			vrepo.save(v1);
			vrepo.save(v2);
		});
	}
}
