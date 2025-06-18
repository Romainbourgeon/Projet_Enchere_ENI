package org.example.super_projet_eni.controller;

import org.example.super_projet_eni.bll.ArticleAVendreService;
import org.example.super_projet_eni.bll.UtilisateurService;
import org.example.super_projet_eni.bo.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String test(Authentication authentication, Model model, @RequestParam(required = false) String motCle,
                       @RequestParam(value = "categorie", required = false) Long categorie,
                       @RequestParam(value = "enchere", required = false) String enchere,
                       @RequestParam(value = "ventes", required = false) String ventes) {

        List<ArticleAVendre> articleAVendres = articleService.listeArticleAVendre();
        List<ArticleAVendre> articleAAfficher = null;

        /* Filtrage sur le nom et la catégorie */

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


        /* Filtrage sur les enchères et les ventes */

        List<Enchere> encheresAAfficher = null;

        if (authentication != null) {
            Utilisateur utilisateurConnecte;
            var principal = authentication.getPrincipal();

            if (principal != null && principal instanceof Utilisateur) {
                utilisateurConnecte = (Utilisateur) principal;
            } else {
                utilisateurConnecte = null;
            }



            if ((enchere != null && !enchere.isEmpty()) || (ventes != null && !ventes.isEmpty())) {

                // A finir
                /*if (enchere.equals("enCours")) {
                    articleAAfficher = articleService.filtreMesEncheresEnCours(utilisateurConnecte);
                }*/

                if (ventes.equals("enCours")) {
                    articleAAfficher = articleAVendres.stream()
                            .filter(a -> a.getVendeur().getPseudo()
                                    .equals(utilisateurConnecte.getPseudo())).toList();
                }

                if (ventes.equals("nonDebutees")) {
                    articleAAfficher = articleService.filtreMesVentesNonDebutees(utilisateurConnecte);
                }

                if (ventes.equals("terminees")) {
                    articleAAfficher = articleService.filtreMesVentesTerminees(utilisateurConnecte);
                }
            }
        }

        model.addAttribute("articleAAfficher", articleAAfficher);
        model.addAttribute("motCle", motCle);
        model.addAttribute("categorieActive", categorie);
        model.addAttribute("encheresAAfficher", encheresAAfficher);

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
