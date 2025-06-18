package org.example.super_projet_eni.controller;

import org.example.super_projet_eni.bll.ArticleAVendreService;
import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.ArticleAVendre;
import org.example.super_projet_eni.bo.Categorie;
import org.example.super_projet_eni.bo.Utilisateur;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@SessionAttributes({ "categoriesEnSession"})
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
    public String test(Model model, @RequestParam(required = false) String motCle,
                       @RequestParam(value = "categorie", required = false) Long categorie) {

        List<ArticleAVendre> articleAVendres = articleService.listeArticleAVendre();
        List<ArticleAVendre> articleAAfficher = null ;

        List<Categorie> list = chargerCategories();

        if (motCle != null && !motCle.isEmpty() && categorie != null) {
            /*articleAVendres = articleService.findByNomAndCategorie(motCle, categorie);*/
        } else if (motCle != null && !motCle.isEmpty()) {
            /*articleAVendres = articleService.findByNom(motCle);*/
        } else if (categorie != null) {
            articleAAfficher = articleAVendres.stream()
                    .filter(a -> {
                        Long articleCatId = a.getCategorie().getId();
                        return articleCatId.equals(categorie);
                    })
                    .toList();
        } else {
            articleAAfficher = articleAVendres;
        }

        model.addAttribute("articleAAfficher",articleAAfficher);
        model.addAttribute("motCle", motCle);
        model.addAttribute("categorieActive", categorie);

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
        // Associer le vendeur connecté
        articleAVendre.setVendeur(vendeur);

        // Charger les objets complets catégorie et adresse
        Categorie categorie = articleService.consulterCategorieById(articleAVendre.getCategorie().getId());
        articleAVendre.setCategorie(categorie);

        Adresse adresse = articleService.consulterAdresseById(articleAVendre.getRetrait().getId());
        articleAVendre.setRetrait(adresse);

        // Initialiser statut et prixVente
        articleAVendre.setStatut(1); // statut "en cours"
        articleAVendre.setPrixVente(0);

        // Créer l'article
        articleService.creerArticleAVendre(articleAVendre); // sauvegarde en BDD

        return "redirect:/accueil";
    }

}