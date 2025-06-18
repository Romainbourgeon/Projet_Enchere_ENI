package org.example.super_projet_eni.controller;

import org.example.super_projet_eni.bll.ArticleAVendreService;
import org.example.super_projet_eni.bo.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@SessionAttributes({"categoriesEnSession"})
public class ArticleAVendreController {

    private final ArticleAVendreService articleService;

    public ArticleAVendreController(ArticleAVendreService articleService) {
        this.articleService = articleService;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        model.addAttribute("dateDuJour", dateStr);
    }

    @ModelAttribute("categoriesEnSession")
    public List<Categorie> chargerCategories() {
        return articleService.listeCategorie();
    }

    @GetMapping("/accueil")
    public String test(Model model,
                       @RequestParam(required = false) String motCle,
                       @RequestParam(value = "categorie", required = false) Long categorieId) {

        List<ArticleAVendre> articleAVendres = articleService.listeArticleAVendre();
        List<ArticleAVendre> articleAAfficher;

        if (motCle != null && !motCle.isEmpty() && categorieId != null) {
            articleAAfficher = articleAVendres.stream()
                    .filter(a -> a.getCategorie() != null && a.getCategorie().getId()==(categorieId))
                    .filter(a -> a.getNom() != null && a.getNom().toLowerCase().contains(motCle.toLowerCase()))
                    .toList();
        } else if (motCle != null && !motCle.isEmpty()) {
            articleAAfficher = articleAVendres.stream()
                    .filter(a -> a.getNom() != null && a.getNom().toLowerCase().contains(motCle.toLowerCase()))
                    .toList();
        } else if (categorieId != null) {
            articleAAfficher = articleAVendres.stream()
                    .filter(a -> a.getCategorie() != null && a.getCategorie().getId()==(categorieId))
                    .toList();
        } else {
            articleAAfficher = articleAVendres;
        }

        model.addAttribute("articleAAfficher", articleAAfficher);
        model.addAttribute("motCle", motCle);
        model.addAttribute("categorieActive", categorieId);

        return "index";
    }

    @GetMapping("/articles/ajouter")
    public String afficherFormVente(Model model) {
        model.addAttribute("articleAVendre", new ArticleAVendre());
        model.addAttribute("categories", articleService.listeCategorie());
        model.addAttribute("adresses", articleService.listeAdresse());
        return "view-new-vente";
    }

    @PostMapping("/articles/ajouter")
    public String submitForm(@ModelAttribute ArticleAVendre articleAVendre,
                             @AuthenticationPrincipal Utilisateur vendeur) {

        // Récupérer la catégorie complète avant de créer l'article
        Categorie categorie = articleService.consulterCategorieById(articleAVendre.getCategorie().getId());
        articleAVendre.setCategorie(categorie);

        // Récupérer l'adresse complète
        Adresse adresse = articleService.consulterAdresseById(articleAVendre.getRetrait().getId());
        articleAVendre.setRetrait(adresse);

        // Associer le vendeur
        articleAVendre.setVendeur(vendeur);

        // Initialisation des champs
        articleAVendre.setStatut(1);
        articleAVendre.setPrixVente(0);

        // Créer l'article et récupérer l'id généré
        long idArticle = articleService.creerArticleAVendre(articleAVendre, vendeur, categorie);

        // Rediriger vers la page d'accueil
        return "redirect:/accueil";
    }

}
