package fi.academy.forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Tässä kontrollerissa ovat kirjautumiseen ja viestien lähettämiseen liittyvät metodit
@Controller
public class Kontrolleri {

    @Autowired
    private ViestiRepositorio repo;
    @Autowired
    private KayttajaRepositorio krepo;

    @GetMapping("/")
    public String listaaViestit (Model model) {
        List<Viesti> viestilista = repo.etsiKaikkiViestiketjut();
        model.addAttribute("viestit", viestilista);
        model.addAttribute("lisattava", new Viesti());
        return "viestiketjut";
    }

    @PostMapping("/viestiketjut")
    public String listaaViestitViestiketjussa (Model model, Viesti viesti) {
        Kayttaja lomakkeeltaTullutKirjautunut = viesti.getKayttaja();
        Optional<Kayttaja> optkirjautunut = lomakkeeltaTullutKirjautunut != null ? krepo.findByNimimerkki(lomakkeeltaTullutKirjautunut.getNimimerkki()) : Optional.empty();
        Kayttaja kirjautunut = null;
        if(optkirjautunut.isPresent()) {
            kirjautunut = optkirjautunut.get();
        }
        List<Viesti> viestilista = repo.etsiKaikkiAikajarjestyksessaID(viesti.getViestiketju());
        model.addAttribute("viestit", viestilista);
        Viesti lisattava = new Viesti(kirjautunut);
        lisattava.setViestiketju(viesti.getViestiketju());
        model.addAttribute("lisattava", lisattava);
        model.addAttribute("kirjautunut", kirjautunut);
        model.addAttribute("admin", kirjautunut!=null?kirjautunut.getAdminoikeus():null);
        return "index";
    }

    // Tää ei löydä käyttäjää mutta ohjaa suoraan virhesivulle ..
    @GetMapping("/omattiedot")
    public String profiilisivu(Viesti viesti, Model model) {
        Kayttaja lomakkeeltaTullutKirjautunut = viesti.getKayttaja();
        Optional<Kayttaja> optkirjautunut = lomakkeeltaTullutKirjautunut
                != null ? krepo.findByNimimerkki(lomakkeeltaTullutKirjautunut.getNimimerkki()) : Optional.empty();
        Kayttaja kirjautunut = null;
        if (optkirjautunut.isPresent()) {
            kirjautunut = optkirjautunut.get();
            model.addAttribute("kayttaja", kirjautunut);
            return "profiili";
        }

        model.addAttribute("viesti", "Profiilitietoja ei löydy!");
        model.addAttribute("luku", 0);
        return "varattu"; // Jos Optional<Kayttaja> onkin null, ohjataan virhesivulle
    }

    @GetMapping("/viestiketjut")
    public String listaaViestiketjut (Model model) {
        List<Viesti> viestiLista = repo.etsiKaikkiViestiketjut();
        model.addAttribute("viestit", viestiLista);
        model.addAttribute("lisattava", new Viesti());
        return "viestiketjut";
    }

    @PostMapping("/")
    public String koti (Viesti viesti, Model model) {
        Optional<Kayttaja> kirjautunut = krepo.findByNimimerkki(viesti.getKayttaja().getNimimerkki());
        System.out.println("!!!!!!!!!!!!!!!!!!!!" + kirjautunut);
        viesti.setKayttaja(kirjautunut.get());



                model.addAttribute("kirjautunut", kirjautunut.get());
                List<Viesti> viestilista = repo.etsiKaikkiViestiketjut();
                model.addAttribute("viestit", viestilista);
                model.addAttribute("lisattava", new Viesti(kirjautunut.get()));
                model.addAttribute("admin", kirjautunut.get().getAdminoikeus());
                return "viestiketjut";



    }


    @PostMapping("/lisaaviesti")
    public String lisaaViesti(@ModelAttribute Viesti viesti, Model model) {
        Optional<Kayttaja> kirjautunut = krepo.findByNimimerkki(viesti.getKayttaja().getNimimerkki());
        Kayttaja k;
        if (kirjautunut.isPresent()) {
            k = kirjautunut.get();
        } else {
            throw new RuntimeException("Kirjautumaton käyttäjä poistanut viestin!");
        }
        viesti.setKayttaja(k);
        k.setViestienMaara((k.getViestienMaara()+1));
        repo.save(viesti);
        List<Viesti> viestilista = repo.etsiKaikkiViestiketjut();
        model.addAttribute("kirjautunut", k);
        model.addAttribute("viestit", viestilista);
        model.addAttribute("lisattava", new Viesti(k));
        model.addAttribute("admin", k.getAdminoikeus());

        return "viestiketjut";

    }

    @PostMapping("/lisaaviestiketju")
    public String lisaaViestiketju(@ModelAttribute Viesti viesti, Model model) {
        Optional<Kayttaja> kirjautunut = krepo.findByNimimerkki(viesti.getKayttaja().getNimimerkki());
        Kayttaja k;
        if (kirjautunut.isPresent()) {
            k = kirjautunut.get();
        } else {
            throw new RuntimeException("Kirjautumaton käyttäjä poistanut viestin!");
        }
        viesti.setKayttaja(k);
        viesti.setViestiketjunAloittaja(1);
        Integer dumppi = repo.etsiViimeisinViestiketjuId();
        viesti.setViestiketju(dumppi+1);
        k.setViestienMaara(k.getViestienMaara()+1);
        repo.save(viesti);
        List<Viesti> viestilista = repo.etsiKaikkiViestiketjut();
        model.addAttribute("viestit", viestilista);
        model.addAttribute("lisattava", new Viesti(k));
        model.addAttribute("admin", k.getAdminoikeus());

        return "viestiketjut";
    }

    @PostMapping("/reply")
    public String replausta(@ModelAttribute Viesti viesti, Model model) {

        Optional<Kayttaja> kirjautunut = krepo.findByNimimerkki(viesti.getKayttaja().getNimimerkki());
        Kayttaja k;
        if (kirjautunut.isPresent()) {
            k = kirjautunut.get();
        } else {
            throw new RuntimeException("Kirjautumaton käyttäjä poistanut viestin!");
        }
        viesti.setKayttaja(k);
        Viesti vastattu = repo.findById(viesti.getVastattuviesti().getId()).get();
        viesti.setTeksti("|| " + vastattu.getKayttaja().getNimimerkki() + ": '" + vastattu.getTeksti() + "' ||                                   " + viesti.getTeksti() );
        k.setViestienMaara(k.getViestienMaara()+1);
        viesti.setViestiketju(vastattu.getViestiketju());
        repo.save(viesti);

        List<Viesti> viestilista = repo.etsiKaikkiViestiketjut();
        model.addAttribute("viestit", viestilista);
        model.addAttribute("lisattava", new Viesti(k));
        model.addAttribute("kirjautunut", k);
        model.addAttribute("admin", k.getAdminoikeus());
        return "viestiketjut";
    }


    @GetMapping("/login")
    public String login (Model model) {

        model.addAttribute("lisattava", new Kayttaja());
        return "login";
    }

    @PostMapping("/kirjaudu")
    public String kirjaudu(Kayttaja kayttaja, Model model) {
        // Tarkistetaan, ettei nimimerkki tai salasana ole tyhjä
        if (kayttaja.getNimimerkki().isEmpty() || kayttaja.getSalasana().isEmpty()) {
            model.addAttribute("viesti", "Syötä sekä nimimerkki että salasana!");
            model.addAttribute("luku", 2);
            return "varattu"; // Ohjataan virhesivulle
        }
        Optional<Kayttaja> kirjautunut = krepo.findByNimimerkki(kayttaja.getNimimerkki());

        if (kirjautunut.isPresent()) {
            // Tarkistetaan, vastaako annettu salasana tietokannasta löytyvää salasanaa
            if (kirjautunut.get().getSalasana().equals(kayttaja.getSalasana())) {

                model.addAttribute("kirjautunut", kirjautunut.get());


                List<Viesti> viestilista = repo.etsiKaikkiViestiketjut();

                model.addAttribute("viestit", viestilista);
                model.addAttribute("lisattava", new Viesti(kayttaja));
                model.addAttribute("admin", kirjautunut.get().getAdminoikeus());
                return "viestiketjut";
            }
            model.addAttribute("viesti", "Väärä salasana!");
            model.addAttribute("luku", 2);
            return "varattu"; // Ohjataan virhesivulle
        }
        model.addAttribute("viesti", "Käyttäjää ei löydy!");
        model.addAttribute("luku", 3);
        return "varattu"; // Ohjataan virhesivulle
    }



    @PostMapping("/poista")
    public String poistaViesti(Viesti viesti,Model model) {

        Optional<Kayttaja> kirjautunut = krepo.findByNimimerkki(viesti.getKayttaja().getNimimerkki());
        Kayttaja k;
        if (kirjautunut.isPresent()) {
            k = kirjautunut.get();
        } else {
            throw new RuntimeException("Kirjautumaton käyttäjä poistanut viestin!");
        }
        k.setViestienMaara(k.getViestienMaara()-1);
        repo.deleteById(viesti.getId());

        List<Viesti> viestilista = repo.etsiKaikkiViestiketjut();
        model.addAttribute("viestit", viestilista);
        model.addAttribute("lisattava", new Viesti(k));
        model.addAttribute("kirjautunut", k);
        model.addAttribute("admin", k.getAdminoikeus());
        return "viestiketjut";
    }

    @GetMapping("/logout")
    public String logout (Model model) {

        //model.addAttribute("lisattava", new Kayttaja());
        return "redirect:";
    }
}