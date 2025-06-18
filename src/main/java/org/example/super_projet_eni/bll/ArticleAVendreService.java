package org.example.super_projet_eni.bll;


import org.example.super_projet_eni.bo.*;

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

    List<ArticleAVendre> findByNomAndCategorie(String motCle, Long categorie);

    List<ArticleAVendre> findByNom(String motCle);

    List<ArticleAVendre> findByCategorie(Long categorie);
    List<Enchere> listeEnchereParUtilisateur(Utilisateur utilisateur);
}



