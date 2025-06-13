package org.example.super_projet_eni.controller; // Adaptez selon votre structure

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.example.super_projet_eni.bll.UtilisateurService;
import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.Utilisateur;
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

    private UtilisateurService utilisateurService;
    private final MotDePassedEncoder motDePasseEncoder;

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
    public String formulaireGet(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "view-inscription";
    }

// Traiter la soumission du formulaire
@PostMapping("/inscription")
public String formulairePost(@ModelAttribute Utilisateur utilisateur, Model model, HttpServletRequest request) {
    var motDePasse = utilisateur.getMotDePasse(); // je save le mdp en clair car besoin plus bas
    utilisateur.setMotDePasse(motDePasseEncoder.encode(motDePasse));
    utilisateurService.registerNewUser(utilisateur);


    // 3. Authentifier via HttpServletRequest
    try {
        request.login(personne.getUsername(), password);
    } catch (ServletException e) {
        // Gérer l'erreur (par exemple, mot de passe incorrect)
        return "redirect:/register?error";
    }

    // 2. Authentifier automatiquement l'utilisateur
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    personne.getUsername(),
                    password // la c'est le mdp en clair qu'il nous faut
            )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    return "redirect:/moutons";
}
}