package fi.academy.forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class RekKontrolleri {

    @Autowired
    KayttajaRepositorio krepo;

    // Rekisteröintilomakkeen tiedot talletetaan uuteen Kayttaja-olioon
    @GetMapping ("/rekisterointi")
    public String syotaTiedot (Model model) {
        model.addAttribute("kayttaja", new Kayttaja());
        return "rekisterointi";
    }

    // Rekisteröintilomakkeelle syötettyjen tietojen käsittely
    @PostMapping ("/profiili")
    public String tallennaTiedot (Kayttaja kayttaja, Model model) {
        Optional<Kayttaja> optKayttaja = krepo.findByNimimerkki(kayttaja.getNimimerkki());

        // Tarkistetaan, löytyykö syötetty nimimerkki jo tietokannasta
        if (!(optKayttaja.isPresent())) {
            // Poissuljetaan tyhjät syötteet
            if (kayttaja.getNimimerkki().isEmpty() || kayttaja.getSalasana().isEmpty()) {
                model.addAttribute("viesti", "Anna sekä nimimerkki että salasana!");
                model.addAttribute("luku", 1);
                return "varattu"; // Ohjaataan käyttäjä virhesivulle
            }
            // Jos kaikki ok, tallennetaan uusi käyttäjä tietokantaan
            krepo.save(kayttaja);
            model.addAttribute("kayttaja", kayttaja);
            return "profiili"; //  Ohjataan käyttäjä profiilisivulle
        }
        model.addAttribute("viesti", "Nimimerkki on jo käytössä!");
        model.addAttribute("luku", 1);
        return "varattu"; // Ohjataan käyttäjä virhesivulle
    }

    // Profiilinmuokkauslomake esitäytetään tietokannassa jo olevilla tiedoilla
    // Tiedot haetaan URLissa olevan idn perusteella
    @GetMapping("/muokkaaprofiilia")
    public String lomake(@RequestParam(name = "id") Integer id, Model model) {
        Optional<Kayttaja> optKaytt = krepo.findById(id);
        if (optKaytt.isPresent()) {
            model.addAttribute("kayttaja", optKaytt.get());
            return "muokkaaprofiilia";
        }
        model.addAttribute("viesti", "Profiilitietoja ei löydy!");
        model.addAttribute("luku", 0);
        return "varattu"; // Jos Optional<Kayttaja> onkin null, ohjataan virhesivulle
    }

    // Profiilinmuokkauslomakkeelle syötettyjen tietojen käsittely
    @PostMapping("/muutatiedot")
    public String muutatietoja(Kayttaja kayttaja, Model model) {
        Optional<Kayttaja> kaytt = krepo.findById(kayttaja.getId());
        Kayttaja k = kaytt.get();

        if(kaytt.isPresent()) {
            // Tallennetaan uuteen Kayttaja-olioon id, nimimerkki ja lähetettyjen viestien määrä kuten aiemmin (niiden muokkaamista ei sallita)
            k.setId(kayttaja.getId());
            k.setNimimerkki(kayttaja.getNimimerkki());
            k.setViestienMaara(kayttaja.getViestienMaara());
            // Muut ominaisuudet haetaan lomakkeelta ja talletetaan
            k.setNimi(kayttaja.getNimi());
            k.setSahkoposti(kayttaja.getSahkoposti());

            // Jos käyttäjä jättää salasanakentän tyhjäksi, säilytetään aiempi salasana
            if (!(kayttaja.getSalasana().isEmpty()))
                k.setSalasana(kayttaja.getSalasana());
            krepo.save(k); // Koska käyttäjän id pysyy samana, luotu uusi olio tietoineen tallentuu vanhojen tietojen päälle
        }
        model.addAttribute("optkayttaja", k);
        return "profiilimuokattu"; // Ohjataan käyttäjä takaisin profiilisivulle
    }

    // Profiilisivunäkymä muiden käyttäjien profiilien katselemiseen
    // Linkki muodostuu URLissa olevan id:n perusteella
    @RequestMapping("/kayttaja")
    public String muuta(@RequestParam(name = "id") Integer id, Model model) {
        Optional<Kayttaja> optKaytt = krepo.findById(id);
        if (optKaytt.isPresent()) {
            model.addAttribute("optkayttaja", optKaytt.get());
            return "profiilimuokattu"; // Näyttää siis profiilisivun, mutta ilman muokkausmahdollisuutta
        }
        model.addAttribute("viesti", "Profiilitietoja ei löydy!");
        model.addAttribute("luku", 0);
        return "varattu"; // Jos Optional<Kayttaja> onkin null, ohjataan virhesivulle
    }
}