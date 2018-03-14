package fi.academy.forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class Kontrolleri {
    Kayttaja kirjautunut;

    @Autowired
    private ViestiRepositorio repo;
    @Autowired
    private KayttajaRepositorio krepo;

    @GetMapping("/")
    public String listaaViestit (Model model) {
        model.addAttribute("viestit", repo.findAll());
        model.addAttribute("lisattava", new Viesti());
        return "index";
    }


    @PostMapping("/lisaaviesti")
    public String lisaaViesti(@ModelAttribute Viesti viesti, Model model) {
        Optional<Kayttaja> kirjautunut = krepo.findByNimimerkki(viesti.getKayttaja().getNimimerkki());
        viesti.setKayttaja(kirjautunut.get());
        repo.save(viesti);
        model.addAttribute("viestit", repo.findAll());
        model.addAttribute("lisattava", new Viesti(kirjautunut.get()));
        model.addAttribute("admin", kirjautunut.get().getAdminoikeus());

        return "index";

    }

    @PostMapping("/reply")
    public String replausta(@ModelAttribute Viesti viesti, Model model) {
        Optional<Kayttaja> kirjautunut = krepo.findByNimimerkki(viesti.getKayttaja().getNimimerkki());
        viesti.setKayttaja(kirjautunut.get());
        viesti.setTeksti("|| käyttäjä: " + viesti.getKayttaja().getNimimerkki() + " sanoi: '" + viesti.getTeksti() + "' || " + viesti.getTeksti() );
        repo.save(viesti);
        model.addAttribute("viestit", repo.findAll());
        model.addAttribute("lisattava", new Viesti(kirjautunut.get()));
        return "index";
    }


    @GetMapping("/login")
    public String login (Model model) {

        model.addAttribute("lisattava", new Kayttaja());
        return "login";
    }


    @PostMapping("/kirjaudu")
    public String kirjaudu(Kayttaja kayttaja, Model model) {
        Optional<Kayttaja> kirjautunut = krepo.findByNimimerkki(kayttaja.getNimimerkki());
        model.addAttribute("kirjautunut", kirjautunut.get());
        model.addAttribute("viestit", repo.findAll());
        model.addAttribute("lisattava", new Viesti(kayttaja));
        model.addAttribute("admin", kirjautunut.get().getAdminoikeus());// tee tl:n puolella: jos admin == 1 -> luo "poista" -napit viestien viereen. jos admin == 0 -> ei tehdä mitään.

        return "index";

    }



    @PostMapping("/poista")
    public String poistaViesti(Viesti viesti, Model model) {
//        System.out.println(id);

        Optional<Kayttaja> kirjautunut = krepo.findByNimimerkki(viesti.getKayttaja().getNimimerkki());
        viesti.setKayttaja(kirjautunut.get());
        System.out.println("!!!!!!" + viesti.getId());
        repo.deleteById(viesti.getId());
        model.addAttribute("viestit", repo.findAll());
        model.addAttribute("lisattava", new Viesti(kirjautunut.get()));
        model.addAttribute("kirjautunut", kirjautunut.get());
        model.addAttribute("admin", kirjautunut.get().getAdminoikeus());



        return "index";
    }



}

