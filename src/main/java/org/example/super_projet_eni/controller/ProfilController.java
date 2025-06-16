package org.example.super_projet_eni.controller;

import org.example.super_projet_eni.bll.UtilisateurService;
import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.Utilisateur;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfilController {

 private final UtilisateurService utilisateurService;

 public ProfilController(UtilisateurService utilisateurService) {
  this.utilisateurService = utilisateurService;
 }

 @GetMapping("/profil")
 public String afficherProfil(Model model) {
  Utilisateur utilisateur = new Utilisateur();
  utilisateur.setPseudo("mockUser");
  utilisateur.setNom("Dupont");
  utilisateur.setPrenom("Jean");
  utilisateur.setEmail("jean.dupont@example.com");
  utilisateur.setTelephone("0123456789");
  utilisateur.setCredit(100);
  utilisateur.setAdmin(false);

  Adresse adresse = new Adresse();
  adresse.setId(1);
  adresse.setRue("10 rue des Fleurs");
  adresse.setCodePostal("75000");
  adresse.setVille("Paris");

  utilisateur.setAdresse(adresse);

  model.addAttribute("utilisateur", utilisateur);

  return "view-profil"; // adapte selon ton fichier Thymeleaf
 }





 /*@GetMapping("/profil")
 public String afficherProfil(Model model) {
  // 🔐 Récupération du pseudo de l'utilisateur connecté
  String pseudo = SecurityContextHolder.getContext().getAuthentication().getName();

  // 🔍 Récupération des infos de l'utilisateur depuis la BDD
  Utilisateur utilisateur = utilisateurService.consulterUtilisateurByPseudo(pseudo);

  // 📦 Injection dans le modèle
  model.addAttribute("utilisateur", utilisateur);
  return "view-profil";
 }*/
}
