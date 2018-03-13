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
    private KayttajaRepositorio krepo;

    @GetMapping("/")
    public String listaaViestit (Model model) {
        model.addAttribute("viestit", repo.findAll());
        model.addAttribute("lisattava", new Viesti());
        return "index";
    }


    @PostMapping("/lisaaviesti")
    public String lisaaViesti(@ModelAttribute Viesti viesti, Model model) {
        repo.save(new Viesti(viesti.getTeksti(), kirjautunut));
        model.addAttribute("viestit", repo.findAll());
        model.addAttribute("lisattava", new Viesti());

        return "index";

    }

    @GetMapping("/login")
    public String login (Model model) {

        model.addAttribute("lisattava", new Kayttaja());
        return "login";
    }


    @PostMapping("/kirjaudu")
    public String kirjaudu(@ModelAttribute Kayttaja kayttaja, Model model) {
        Optional kirjautunut = krepo.findByNimimerkki(kayttaja.getNimimerkki());
        if (kirjautunut.isPresent())
        model.addAttribute("kirjautunut", kirjautunut);
        model.addAttribute("lisattava", new Viesti());

        return "index";

    }



}

