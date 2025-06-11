package controller; // Adaptez selon votre structure

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccueilController {

    @GetMapping("/")
    public String accueil() {
        return "index";
    }

    @GetMapping("/connexion")
    public String connexion() {

        return "view-connexion";
    }
}