package org.example.super_projet_eni.controller;

import org.example.super_projet_eni.bll.ArticleAVendreService;
import org.example.super_projet_eni.bo.ArticleAVendre;
import org.example.super_projet_eni.bo.Categorie;
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
        System.out.println(list);

        if (motCle != null && !motCle.isEmpty() && categorie != null) {
           List<ArticleAVendre> filtreParCategorie = null;
           filtreParCategorie = articleAVendres.stream()
                   .filter(a -> {
                       Long articleCatId = a.getCategorie().getId();
                       return articleCatId.equals(categorie);
                   })
                   .toList();

           articleAAfficher = filtreParCategorie.stream()
                   .filter(a -> a.getNom().toLowerCase().contains(motCle.toLowerCase()))
                   .toList();

        } else if (motCle != null && !motCle.isEmpty()) {
            articleAAfficher = articleAVendres.stream()
                    .filter(a -> a.getNom().toLowerCase().contains(motCle.toLowerCase()))
                    .toList();
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


/*    @GetMapping("/articles/ajouter")
    public String showForm(Model model) {
        model.addAttribute("articleAVendre", new ArticleAVendre());
        model.addAttribute("categories", articleService.listeCategorie());
        model.addAttribute("adresses", articleService.listeAdresse());
        return "article_form"; // nom de ta page Thymeleaf
    }

    @PostMapping("/articles/ajouter")
    public String submitForm(@ModelAttribute ArticleAVendre articleAVendre,
                             @AuthenticationPrincipal Utilisateur vendeur) {
        // Associer le vendeur connecté à l'article
        articleAVendre.setVendeur(vendeur);

        // Assure-toi que les objets categorie et retrait sont bien chargés (si seulement id reçu)
        Categorie categorie = articleService.consulterCategorieById(articleAVendre.getCategorie().getId());
        articleAVendre.setCategorie(categorie);
        Adresse adresse = articleService.consulterAdresseById(articleAVendre.getRetrait().getId());
        articleAVendre.setRetrait(adresse);

        // Initialisation de statut, prixVente, etc si besoin
        articleAVendre.setStatut(1); // par exemple "En cours"
        articleAVendre.setPrixVente(0); // prix de départ

        articleService.creerArticleAVendre(articleAVendre);

        return "redirect:/accueil"; // redirection vers la page d'accueil
    }*/
}










