package org.example.super_projet_eni.bll.mock;

import org.example.super_projet_eni.bll.ArticleAVendreService;
import bo.Adresse;
import bo.ArticleAVendre;
import bo.Categorie;
import bo.Utilisateur;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Profile("dev")
public class ArticleAVendreBouchon implements ArticleAVendreService {


    private static List<Utilisateur> lstutilisateurs = new ArrayList<>();
    private static List<ArticleAVendre> lstarticleAVendres = new ArrayList<>();
    private static List<Adresse> lstadresses = new ArrayList<>();
    private static List<Categorie> lstcategories = new ArrayList<>();
    private static int indexArticleAvendres = 1;

    @Override
    public List<ArticleAVendre> listeArticleAVendre() {
        return lstarticleAVendres;
    }
    @Override
    public ArticleAVendre consulterArticleAVendreById(long id) {
        return lstarticleAVendres.stream().filter(item -> item.getId() == id).findAny().orElse(null);
    }


    @Override
    public List<Adresse> listeAdresse() {
        return lstadresses;
    }
    @Override
    public Adresse consulterAdresseById(long id) {
        return lstadresses.stream().filter(item -> item.getId() == id).findAny().orElse(null);
    }


    @Override
    public List<Utilisateur> listeUtilisateur() {
        return lstutilisateurs;
    }
    @Override
    public Utilisateur consulterUtilisateurById(String pseudo) {
        return lstutilisateurs.stream().filter(item -> item.getPseudo() == pseudo).findAny().orElse(null);
    }


    @Override
    public List<Categorie> listeCategorie() {
        return lstcategories;
    }
    @Override
    public Categorie consulterCategorieById(long id) {
        return lstcategories.stream().filter(item -> item.getId() == id).findAny().orElse(null);
    }



    @Override
    public void creerArticleAVendre(ArticleAVendre articleAVendre) {

        articleAVendre.setId(indexArticleAvendres++);
        lstarticleAVendres.add(articleAVendre);
    }







}
