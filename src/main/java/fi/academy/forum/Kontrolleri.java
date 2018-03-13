package fi.academy.forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Kontrolleri {

    @Autowired
    private ViestiRepositorio repo;

    @GetMapping("/")
    public String listaaViestit (Model model) {
        model.addAttribute("viestit", repo.findAll());
        model.addAttribute("lisattava", new Viesti());
        return "index";
    }


    @PostMapping("/lisaaviesti")
    public String lisaaViesti(@ModelAttribute Viesti viesti, Model model) {
        repo.save(viesti);
        model.addAttribute("viestit", repo.findAll());
        model.addAttribute("lisattava", new Viesti());

        return "index";

    }




}

