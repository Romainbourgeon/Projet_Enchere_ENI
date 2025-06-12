package org.example.super_projet_eni.controller; // Adaptez selon votre structure

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ConnectionController {

    @GetMapping("/")
    public String accueil() {
        return "index.html";
    }

    @GetMapping("/connexion")
    public String connexion() {
        return "view-connexion";
    }
}