package org.example.super_projet_eni.controller; // Adaptez selon votre structure

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.example.super_projet_eni.bll.UtilisateurService;
import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.Utilisateur;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConnectionController {

    private final AuthenticationManager authenticationManager;
    private final UtilisateurService utilisateurService;
    private final PasswordEncoder motDePasseEncoder;

    public ConnectionController(AuthenticationManager authenticationManager, UtilisateurService utilisateurService, PasswordEncoder motDePasseEncoder) {
        this.authenticationManager = authenticationManager;
        this.utilisateurService = utilisateurService;
        this.motDePasseEncoder = motDePasseEncoder;
    }

    @GetMapping("/")
    public String accueil() {
        return "index";
    }

    @GetMapping("/connexion")
    public String connexion() {
        return "view-connexion";
    }

    @GetMapping("/inscription")
    public String registerGet(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "view-inscription";
    }

    // Traiter la soumission du formulaire
    @PostMapping("/inscription")
    public String registerPost(@ModelAttribute Utilisateur utilisateur, Model model, HttpServletRequest request) {
        // validation du mot de passe égal à la confirmation à faire ici
        if (!utilisateur.getMotDePasse().equals(utilisateur.getConfirmeMotDePasse())) {
            model.addAttribute("error", "Les mots de passe ne correspondent pas.");
            return "view-inscription";
        }

        String motDePasseEnClair = utilisateur.getMotDePasse();
        utilisateur.setMotDePasse(motDePasseEncoder.encode(motDePasseEnClair));

        // gérer insertion adresse et utilisateur (adresse dans utilisateur)
        utilisateurService.ajouterUtilisateur(utilisateur, utilisateur.getAdresse());

        try {
            // Authentification via request.login()
            request.login(utilisateur.getPseudo(), motDePasseEnClair);
        } catch (ServletException e) {
            // erreur d’authentification
            return "redirect:/register?error";
        }

        // 2. Authentifier automatiquement l'utilisateur
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        utilisateur.getPseudo(),
                        motDePasseEnClair // la c'est le mdp en clair qu'il nous faut
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/view-connexion"; // ou page d’accueil connectée
    }
}

