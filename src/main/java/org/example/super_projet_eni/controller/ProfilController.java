package org.example.super_projet_eni.controller;

import org.example.super_projet_eni.bll.UtilisateurService;
import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.Utilisateur;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class ProfilController {

 private final UtilisateurService utilisateurService;

 public ProfilController(UtilisateurService utilisateurService) {
  this.utilisateurService = utilisateurService;
 }

 @GetMapping("/profil")
 public String afficherProfil(Model model) {
  //  Récupération du pseudo de l'utilisateur connecté
  String pseudo = SecurityContextHolder.getContext().getAuthentication().getName();

  //  Récupération des infos de l'utilisateur depuis la BDD
  Utilisateur utilisateur = utilisateurService.consulterUtilisateurByPseudo(pseudo);

  long adresseID = utilisateur.getAdresse().getId();
  Adresse adresse = utilisateurService.voirAdresseParId((int) adresseID);

  //  Injection dans le modèle
  model.addAttribute("utilisateur", utilisateur);
  model.addAttribute("adresse", adresse);
  return "view-profil";
 }


 @GetMapping("/profil/modifier")
 public String afficherFormulaireModification(Model model) {
  String pseudo = SecurityContextHolder.getContext().getAuthentication().getName();
  Utilisateur utilisateur = utilisateurService.consulterUtilisateurByPseudo(pseudo);

  long adresseID = utilisateur.getAdresse().getId();
  Adresse adresse = utilisateurService.voirAdresseParId((int) adresseID);

  model.addAttribute("utilisateur", utilisateur);
  model.addAttribute("adresse", adresse);
  return "view-modif-profil";  // le nom de ta vue Thymeleaf
 }



 @PostMapping("/profil/modifier")
 public String enregistrerModifications(@ModelAttribute Utilisateur utilisateurForm, Principal principal) {
  String pseudo = principal.getName();
  Utilisateur utilisateurEnBase = utilisateurService.consulterUtilisateurByPseudo(pseudo);

  // Mise à jour des champs simples
  utilisateurEnBase.setEmail(utilisateurForm.getEmail());
  utilisateurEnBase.setTelephone(utilisateurForm.getTelephone());

  // Mise à jour de l'adresse
  Adresse adresseEnBase = utilisateurEnBase.getAdresse();
  Adresse adresseForm = utilisateurForm.getAdresse();

  adresseEnBase.setRue(adresseForm.getRue());
  adresseEnBase.setCodePostal(adresseForm.getCodePostal());
  adresseEnBase.setVille(adresseForm.getVille());

  utilisateurService.update(utilisateurEnBase);

  return "redirect:/profil";
 }
}
