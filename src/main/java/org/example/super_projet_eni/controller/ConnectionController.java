package org.example.super_projet_eni.controller; // Adaptez selon votre structure

import org.example.super_projet_eni.bll.UtilisateurService;
import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.Utilisateur;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConnectionController {

private UtilisateurService utilisateurService;

    public ConnectionController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/")
    public String accueil() {
        return "index.html";
    }

    @GetMapping("/connexion")
    public String connexion() {
        return "view-connexion";
    }

    @GetMapping("/inscription")
    public String afficherFormulaireInscription(Model model) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setAdresse(new Adresse());
        model.addAttribute("utilisateur", utilisateur);
        return "view-inscription";
    }

    @PostMapping("/inscription")
    public String traiterInscription(@ModelAttribute("utilisateur") Utilisateur utilisateur, BindingResult result, Model model) {
        utilisateur.setCredit(10); // valeur par défaut
        utilisateur.setAdmin(false); // par défaut non admin

        try {
            utilisateurService.ajouterUtilisateur(utilisateur);
        } catch (IllegalArgumentException e) {
            result.rejectValue("pseudo", "error.utilisateur", e.getMessage());
            return "view-inscription";
        }

        return "redirect:/connexion";
    }
}