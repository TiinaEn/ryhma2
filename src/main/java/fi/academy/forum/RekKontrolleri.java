package fi.academy.forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class RekKontrolleri {

    @Autowired
    KayttajaRepositorio krepo;

    @GetMapping ("/rekisterointi")
    public String syotaTiedot (Model model) {
        model.addAttribute("kayttaja", new Kayttaja());
        return "rekisterointi";
    }

    @PostMapping ("/profiili")
    public String tallennaTiedot (Kayttaja kayttaja, Model model) {
        Optional<Kayttaja> optKayttaja = krepo.findByNimimerkki(kayttaja.getNimimerkki());
        if (!(optKayttaja.isPresent())) {
            krepo.save(kayttaja);
            model.addAttribute("kayttaja", kayttaja);
        }
        return "rekistorointi";
    }
}
