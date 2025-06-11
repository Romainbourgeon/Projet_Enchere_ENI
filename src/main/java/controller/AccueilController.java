package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccueilController {

    @GetMapping("/")
    public String accueil(Model model) {
        model.addAttribute("pageTitle", "marteau.png");
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about"; // crée about.html plus tard si tu veux
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact"; // crée contact.html plus tard
    }
}