package fi.academy.forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class Kontrolleri {
    //Kayttaja kirjautunut;

    @Autowired
    private ViestiRepositorio repo;
    @Autowired
    private KayttajaRepositorio krepo;

    @GetMapping("/")
    public String listaaViestit (Model model) {
        /*model.addAttribute("viestit", repo.findAll());*/
        List<Viesti> viestilista = repo.etsiKaikkiAikajarjestyksessa();
        model.addAttribute("viestit", viestilista);
        model.addAttribute("lisattava", new Viesti());
        return "viestiketjut";
    }

    @PostMapping("/viestiketjut")
    public String listaaViestitViestiketjussa (Model model, Viesti viesti) {
        List<Viesti> viestilista = repo.etsiKaikkiAikajarjestyksessaID(viesti.getViestiketju());
        model.addAttribute("viestit", viestilista);
        model.addAttribute("lisattava", new Viesti());
        return "index";
    }

    @GetMapping("/viestiketjut")
    public String listaaViestiketjut (Model model) {
        /*model.addAttribute("viestit", repo.findAll());*/
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
              /*  model.addAttribute("viestit", repo.findAll());*/
                List<Viesti> viestilista = repo.etsiKaikkiAikajarjestyksessa();
                model.addAttribute("viestit", viestilista);
                model.addAttribute("lisattava", new Viesti(kirjautunut.get()));
                model.addAttribute("admin", kirjautunut.get().getAdminoikeus());
                return "viestiketjut";



//        Optional<Kayttaja> kirjautunut = krepo.findByNimimerkki(viesti.getKayttaja().getNimimerkki());
//        viesti.setKayttaja(kirjautunut.get());
//        System.out.println("!!!!!!" + viesti.getId());
//        repo.deleteById(viesti.getId());
//        model.addAttribute("viestit", repo.findAll());
//        model.addAttribute("lisattava", new Viesti(kirjautunut.get()));
//        model.addAttribute("kirjautunut", kirjautunut.get());
//        model.addAttribute("admin", kirjautunut.get().getAdminoikeus());


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
        repo.save(viesti);
        List<Viesti> viestilista = repo.etsiKaikkiAikajarjestyksessa();
        model.addAttribute("kirjautunut", k);
        model.addAttribute("viestit", viestilista);
        model.addAttribute("lisattava", new Viesti(k));
        model.addAttribute("admin", k.getAdminoikeus());

        return "index";

    }

    @PostMapping("/lisaaviestiketju")
    public String lisaaViestiketju(@ModelAttribute Viesti viesti, Model model) {
        Optional<Kayttaja> kirjautunut = krepo.findByNimimerkki(viesti.getKayttaja().getNimimerkki());
        viesti.setKayttaja(kirjautunut.get());
        viesti.setViestiketjunAloittaja(1);
        Integer dumppi = repo.etsiViimeisinViestiketjuId();
        viesti.setViestiketju(dumppi+1);
        repo.save(viesti);
        List<Viesti> viestilista = repo.etsiKaikkiAikajarjestyksessa();
        model.addAttribute("viestit", viestilista);
        model.addAttribute("lisattava", new Viesti(kirjautunut.get()));
        model.addAttribute("admin", kirjautunut.get().getAdminoikeus());

        return "viestiketjut";

    }

    @PostMapping("/reply")
    public String replausta(@ModelAttribute Viesti viesti, Model model) {
        Optional<Kayttaja> kirjautunut = krepo.findByNimimerkki(viesti.getKayttaja().getNimimerkki());
        viesti.setKayttaja(kirjautunut.get());
        Viesti vastattu = repo.findById(viesti.getVastattuviesti().getId()).get();
        viesti.setTeksti("|| " + vastattu.getKayttaja().getNimimerkki() + ": '" + vastattu.getTeksti() + "' ||" + viesti.getTeksti() );
        repo.save(viesti);
       /* model.addAttribute("viestit", repo.findAll());*/
        List<Viesti> viestilista = repo.etsiKaikkiAikajarjestyksessa();
        model.addAttribute("viestit", viestilista);
        model.addAttribute("lisattava", new Viesti(kirjautunut.get()));
        model.addAttribute("kirjautunut", kirjautunut.get());
        model.addAttribute("admin", kirjautunut.get().getAdminoikeus());
        return "index";
    }


    @GetMapping("/login")
    public String login (Model model) {

        model.addAttribute("lisattava", new Kayttaja());
        return "login";
    }


    @PostMapping("/kirjaudu")
    public String kirjaudu(Kayttaja kayttaja, Model model) {
        if (kayttaja.getNimimerkki().isEmpty() || kayttaja.getSalasana().isEmpty()) {
            model.addAttribute("viesti", "Syötä sekä nimimerkki että salasana!");
            model.addAttribute("luku", 2);
            return "varattu";
        }
        Optional<Kayttaja> kirjautunut = krepo.findByNimimerkki(kayttaja.getNimimerkki());

        if (kirjautunut.isPresent()) {
            if (kirjautunut.get().getSalasana().equals(kayttaja.getSalasana())) {

                model.addAttribute("kirjautunut", kirjautunut.get());
                /*model.addAttribute("viestit", repo.findAll());*/
                List<Viesti> viestilista = repo.etsiKaikkiAikajarjestyksessa();
                model.addAttribute("viestit", viestilista);
                model.addAttribute("lisattava", new Viesti(kayttaja));
                model.addAttribute("admin", kirjautunut.get().getAdminoikeus());
                return "viestiketjut";
            }
            model.addAttribute("viesti", "Väärä salasana!");
            model.addAttribute("luku", 2);
            return "varattu";
        }
        model.addAttribute("viesti", "Käyttäjää ei löydy!");
        model.addAttribute("luku", 3);
        return "varattu";
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
        repo.deleteById(viesti.getId());

        List<Viesti> viestilista = repo.etsiKaikkiAikajarjestyksessa();
        model.addAttribute("viestit", viestilista);


        model.addAttribute("lisattava", new Viesti(k));
        model.addAttribute("kirjautunut", k);
        model.addAttribute("admin", k.getAdminoikeus());



        return "index";
    }

    @GetMapping("/logout")
    public String logout (Model model) {

        //model.addAttribute("lisattava", new Kayttaja());
        return "redirect:";
    }



}

