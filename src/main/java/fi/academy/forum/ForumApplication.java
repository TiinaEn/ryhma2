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
			admin.setAdminoikeus(1);
			admin.setViestienMaara(1);
			krepo.save(admin);
			Kayttaja user = new Kayttaja("user", "user");

            Kayttaja niki = new Kayttaja("niki", "niki");
            Kayttaja tiina = new Kayttaja("tiina", "tiina");
            Kayttaja renne = new Kayttaja("renne", "renne");
            Kayttaja elina = new Kayttaja("elina", "elina");
            niki.setViestienMaara(1);
            renne.setViestienMaara(1);
            elina.setViestienMaara(1);
            tiina.setViestienMaara(2);
            krepo.save(niki);
            krepo.save(tiina);
            krepo.save(renne);
            krepo.save(elina);
			user.setViestienMaara(2);
			krepo.save(user);
			Viesti v1 = new Viesti("Minulla on corgi ja se on mahtava karvapötkylä", admin, 1, 1, "Mp koirat?");
			Viesti v2 = new Viesti("Koirat ovat mielestäni lutuisia ihkupalleroita :3333", user, 1);
			Viesti v3 = new Viesti("Mielestäni kissat ovat ihania lutupalleroita :333", user, 2, 1, "Mitä mieltä kissoista????");
			Viesti v4 = new Viesti("Olen allerginen kissoille, yöksistä!", renne, 2);
            Viesti v5 = new Viesti("En tiedä, onko tämä tyhmä kysymys. Olen ensimmäistä kertaa ikinä lähtemässä lentäen ulkomaille. Olen käynyt autoreissuilla Euroopassa ja laivoilla Ruotsissa ja Virossa.\n" +
                    "\n" +
                    "Lento lähtee vähän ennen puoltayötä ja lentomatka on 13 tuntia, lentokoneessa pitäisi siis nukkua. Onko siellä peitot ja tyynyt? Vaihtaako kaikki pyjamat päälle vai nukutaanko siellä päivävaatteissa? Onko lentokoneissa mitään mahdollisuutta pestä meikkejä pois, pestä hampaita aamulla jne? Vai onko siellä vain joku pieni WC-koppi, niin kuin joissain pitkän matkan busseissa?\n" +
                    "\n" +
                    "Kiitos, jos joku viitsii vastata.", niki, 3, 1, "Vaihdetaanko lentokoneissa yölennoilla pyjamat päälle?");
            Viesti v6 = new Viesti("Tän täytyy olla joku vitsi..", tiina, 3);
            Viesti v7 = new Viesti("Tottakai vaihdetaan pyjamat päälle! Nyt on vuosi 2018, eikä eletä enää kivikaudella!", elina, 3);
            Viesti v8 = new Viesti("Meille kotiutui koira, jonka menneisyydestä emme tiedä kovinkaan paljon. Ongelmana meillä on se, että pelkää omaa peilikuvaansa hyvin paljon. Murisee, ärisee, ja ottaa puolustusasennon hampaat paljastaen. Livekoirille ei näin reagoi. Kipa on reilu 2-vuotias narttu. Mistä ja miten aloittaisimme ongelman ratkaisun?\n" +
                    "\n" +
                    "Kiitos etukäteen asiallisista kommenteista.", tiina, 1);
            vrepo.save(v1);
			vrepo.save(v2);
			vrepo.save(v3);
            vrepo.save(v4);
            vrepo.save(v5);
            vrepo.save(v6);
            vrepo.save(v7);
            vrepo.save(v8);
		});
	}
}
