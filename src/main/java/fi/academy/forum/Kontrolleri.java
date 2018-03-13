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

        return "index";

    }



}

