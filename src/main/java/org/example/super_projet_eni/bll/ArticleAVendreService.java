package org.example.super_projet_eni.bll;

import bo.Adresse;
import bo.ArticleAVendre;
import bo.Categorie;
import bo.Utilisateur;
import java.util.List;

public interface ArticleAVendreService {

    List<ArticleAVendre> listeArticleAVendre();
ArticleAVendre consulterArticleAVendreById(long id);

List<Adresse> listeAdresse();
Adresse consulterAdresseById(long id);

List<Utilisateur> listeUtilisateur();
Utilisateur consulterUtilisateurById(String pseudo);


    List<Categorie> listeCategorie();
    Categorie consulterCategorieById(long id);

    void creerArticleAVendre(ArticleAVendre articleAVendre);
}
