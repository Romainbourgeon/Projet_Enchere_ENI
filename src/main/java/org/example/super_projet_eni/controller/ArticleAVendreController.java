package org.example.super_projet_eni.controller;

import org.example.super_projet_eni.bll.ArticleAVendreService;
import org.example.super_projet_eni.bo.ArticleAVendre;
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
    public String accueil(Model model, @RequestParam(required = false) String categorie) {
        List<ArticleAVendre> articles;
        if (categorie != null) {
            articles = articleService.listeArticleAVendre(); // à remplacer par getByCategorie si disponible
        } else {
            articles = articleService.listeArticleAVendre();
        }
        model.addAttribute("articles", articles);
        model.addAttribute("categorieActive", categorie);
        return "index"; // le nom du template Thymeleaf sans extension
    }
}
