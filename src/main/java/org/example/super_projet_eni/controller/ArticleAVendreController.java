package org.example.super_projet_eni.controller;

import org.example.super_projet_eni.bll.ArticleAVendreService;
import org.example.super_projet_eni.bo.ArticleAVendre;
import org.example.super_projet_eni.bo.Categorie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
                       @RequestParam(required = false) Long categorie) {

        List<ArticleAVendre> articleAVendres = articleService.listeArticleAVendre();
        List<ArticleAVendre> articleAAfficher = null ;

        if (motCle != null && !motCle.isEmpty() && categorie != null) {
            /*articleAVendres = articleService.findByNomAndCategorie(motCle, categorie);*/
        } else if (motCle != null && !motCle.isEmpty()) {
            /*articleAVendres = articleService.findByNom(motCle);*/
        } else if (categorie != null) {
            articleAAfficher = articleAVendres.stream().filter(a -> a.getCategorie().equals(articleService.consulterCategorieById(categorie))).toList();
        } else {
            articleAAfficher = articleAVendres;
        }

        model.addAttribute("articleAAfficher",articleAAfficher);

        return "index";
    }




   /* @GetMapping("/test")
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
        *//*model.addAttribute("categories", categories);*//*
        model.addAttribute("motCle", motCle);
        *//*model.addAttribute("categorieActive", categorie);*//*

        return "index";
    }*/

}









