package fi.academy.forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            if (kayttaja.getNimimerkki().isEmpty() || kayttaja.getSalasana().isEmpty()) {
                model.addAttribute("viesti", "Anna sekä nimimerkki että salasana!");
                model.addAttribute("luku", 1);
                return "varattu";
            }
            krepo.save(kayttaja);
            model.addAttribute("kayttaja", kayttaja);
            return "profiili";
        }
        model.addAttribute("viesti", "Nimimerkki on jo käytössä!");
        model.addAttribute("luku", 1);
        return "varattu";
    }

    @GetMapping("/muokkaaprofiilia")
    public String lomake(@RequestParam(name = "id") Integer id, Model model) {
        Optional<Kayttaja> optKaytt = krepo.findById(id);
        if (optKaytt.isPresent()) {
            model.addAttribute("kayttaja", optKaytt.get());
            return "muokkaaprofiilia";
        }
        model.addAttribute("viesti", "Profiilitietoja ei löydy!");
        model.addAttribute("luku", 0);
        return "varattu";
    }

    @PostMapping("/muutatiedot")
    public String muutatietoja(Kayttaja kayttaja, Model model) {
        Optional<Kayttaja> kaytt = krepo.findById(kayttaja.getId());
        Kayttaja k = kaytt.get();

        if(kaytt.isPresent()) {
            k.setId(kayttaja.getId());
            k.setNimimerkki(kayttaja.getNimimerkki());
            k.setNimi(kayttaja.getNimi());
            k.setSahkoposti(kayttaja.getSahkoposti());

            if (!(kayttaja.getSalasana().isEmpty()))
                k.setSalasana(kayttaja.getSalasana());
            krepo.save(k);
        }
        model.addAttribute("optkayttaja", k);
        return "profiilimuokattu";
    }
}
