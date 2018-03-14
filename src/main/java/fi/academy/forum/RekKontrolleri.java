package fi.academy.forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
            krepo.save(kayttaja);
            model.addAttribute("kayttaja", kayttaja);
            return "profiili";
        }
        return "varattu";
    }

    @GetMapping("/muokkaaprofiilia")
    public String lomake(@RequestParam(name = "id") Integer id, Model model) {
        Optional<Kayttaja> optKaytt = krepo.findById(id);
        if (optKaytt.isPresent()) {
            model.addAttribute("kayttaja", optKaytt.get());
            return "muokkaaprofiilia";
        }
        throw new RuntimeException("Virhe");
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
            k.setSalasana(kayttaja.getSalasana());
            krepo.save(k);
        }
        model.addAttribute("optkayttaja", k);
        return "profiilimuokattu";
    }
}
