package org.example.super_projet_eni.controller;

import org.example.super_projet_eni.bll.ArticleAVendreService;
import org.example.super_projet_eni.bo.ArticleAVendre;
import org.example.super_projet_eni.bo.Categorie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ArticleAVendreController {

    private final ArticleAVendreService articleService;

    public ArticleAVendreController(ArticleAVendreService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/accueil")
    public String accueil(
            Model model,
            @RequestParam(required = false) String motCle,
            @RequestParam(required = false) Long categorie) {

        List<ArticleAVendre> articles;

        if (motCle != null && !motCle.isEmpty() && categorie != null) {
            articles = articleService.findByNomAndCategorie(motCle, categorie);
        } else if (motCle != null && !motCle.isEmpty()) {
            articles = articleService.findByNom(motCle);
        } else if (categorie != null) {
            articles = articleService.findByCategorie(categorie);
        } else {
            articles = articleService.listeArticleAVendre();
        }

        List<Categorie> categories = articleService.listeCategorie();

        model.addAttribute("articles", articles);
        model.addAttribute("categories", categories);
        model.addAttribute("motCle", motCle);
        model.addAttribute("categorieActive", categorie);

        return "index";
    }

}









