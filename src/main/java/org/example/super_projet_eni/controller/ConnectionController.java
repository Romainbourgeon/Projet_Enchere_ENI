package org.example.super_projet_eni.controller; // Adaptez selon votre structure

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.super_projet_eni.bll.UtilisateurService;
import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.Utilisateur;
import org.example.super_projet_eni.dal.UtilisateurDaoImpl;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
public class ConnectionController {


    private final UtilisateurDaoImpl utilisateur;
    private UtilisateurService utilisateurService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder motDePasseEncoder;

    public ConnectionController(UtilisateurDaoImpl utilisateur, UtilisateurService utilisateurService,
                                PasswordEncoder motDePasseEncoder,
                                AuthenticationManager authenticationManager) {
        this.utilisateur = utilisateur;
        this.utilisateurService = utilisateurService;
        this.motDePasseEncoder = motDePasseEncoder;
        this.authenticationManager = authenticationManager;
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

    @PostMapping("/inscription")
    public String registerPost(@ModelAttribute Utilisateur utilisateur, Model model, HttpServletRequest request) {

        // Vérification des mots de passe
        if (!utilisateur.getMotDePasse().equals(utilisateur.getConfirmeMotDePasse())) {
            model.addAttribute("erreur", "Les mots de passe ne correspondent pas.");
            return "view-inscription";
        }

        // Vérification de l'unicité du pseudo
        Optional<Utilisateur> utilisateurExistant = utilisateurService.voirUtilisateurParPseudo(utilisateur.getPseudo());
        if (utilisateurExistant.isPresent()) {
            model.addAttribute("erreur", "Ce pseudo existe déjà.");
            return "view-inscription";
        }

        // Sauvegarde de l'adresse si nécessaire
        if (utilisateur.getAdresse() != null && utilisateur.getAdresse() == null) {
            utilisateurService.creerAdresse(utilisateur.getAdresse());
        }

        // Encodage du mot de passe avant création
        var motDePasseEnClair = utilisateur.getMotDePasse(); // mot de passe en clair pour la suite
        utilisateur.setMotDePasse(motDePasseEncoder.encode(motDePasseEnClair));

        // Création de l'utilisateur en base
        utilisateurService.ajouterUtilisateur(utilisateur);

        // Authentification automatique
        try {
            // Utilisation de HttpServletRequest.login (possible si configuré)
            request.login(utilisateur.getPseudo(), motDePasseEnClair);
        } catch (ServletException e) {
            model.addAttribute("erreur", "Erreur d'authentification après inscription.");
            return "redirect:/register?error";
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(utilisateur.getPseudo(), motDePasseEnClair));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/connexion";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/accueil";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        model.addAttribute("dateDuJour", dateStr);
    }
}