package org.example.super_projet_eni.bll;


import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.ArticleAVendre;
import org.example.super_projet_eni.bo.Categorie;
import org.example.super_projet_eni.bo.Utilisateur;
import org.example.super_projet_eni.dal.AdresseDao;
import org.example.super_projet_eni.dal.ArticleAVendreDao;
import org.example.super_projet_eni.dal.CategorieDao;
import org.example.super_projet_eni.dal.UtilisateurDao;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Primary
public class ArticleAVendreImpl implements ArticleAVendreService {


     private UtilisateurDao utilisateurDao ;
     private ArticleAVendreDao articleAVendreDao ;
     private AdresseDao adresseDao;
     private CategorieDao categorieDao;


public ArticleAVendreImpl(UtilisateurDao utilisateurDao, ArticleAVendreDao articleAVendreDao,AdresseDao adresseDao,CategorieDao categorieDao) {
    this.utilisateurDao = utilisateurDao;
    this.articleAVendreDao = articleAVendreDao;
    this.adresseDao = adresseDao;
    this.categorieDao = categorieDao;

}


    @Override
    public List<ArticleAVendre> listeArticleAVendre() {
        return articleAVendreDao.readdAll();
    }

    @Override
    public ArticleAVendre consulterArticleAVendreById(long id) {
        return articleAVendreDao.read(id);
    }

    @Override
    public List<Adresse> listeAdresse() {
        return adresseDao.readAll();
    }

    @Override
    public Adresse consulterAdresseById(long id) {
        return adresseDao.read(id);
    }

    @Override
    public List<Utilisateur> listeUtilisateur() {
        return utilisateurDao.readAll();
    }

    @Override
    public Utilisateur consulterUtilisateurById(String pseudo) {
        return utilisateurDao.read(pseudo);
    }

    @Override
    public List<Categorie> listeCategorie() {
        return categorieDao.readAll();
    }

    @Override
    public Categorie consulterCategorieById(long id) {
        return categorieDao.read(id);
    }

    @Override
    public void creerArticleAVendre(ArticleAVendre articleAVendre) {

    }
}
