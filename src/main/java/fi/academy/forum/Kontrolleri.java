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
    private OppilasRepository repo;

    @GetMapping("/")
    public String listaaOppilaat (Model model) {
        model.addAttribute("viestit", repo.findAll());
        model.addAttribute("dummy", new Oppilas());
        return "index";
    }


    @PostMapping("/lisaaoppilas")
    public String lisaaOppilas(@ModelAttribute Oppilas oppilas, Model model) {
        repo.save(oppilas);
        model.addAttribute("oppilaat", repo.findAll());
        model.addAttribute("dummy", new Oppilas());

        return "oppilaat";

    }




}

