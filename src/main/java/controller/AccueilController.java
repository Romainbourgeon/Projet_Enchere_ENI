package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccueilController {

    @GetMapping("/")
    public String accueil() {
        return "index";
    }

    @GetMapping("/connexion")
    public String connexion() {
        return "view/view-connexion";
    }

}