package org.example.super_projet_eni.bll;


import org.example.super_projet_eni.bo.*;
import org.example.super_projet_eni.dal.*;

import org.springframework.stereotype.Service;

import java.util.List;


@Service

public class ArticleAVendreServiceImpl implements ArticleAVendreService {


    private UtilisateurDao utilisateurDao;
    private ArticleAVendreDao articleAVendreDao;
    private AdresseDao adresseDao;
    private CategorieDao categorieDao;
    private EnchereDao enchereDao;


    public ArticleAVendreServiceImpl(UtilisateurDao utilisateurDao, ArticleAVendreDao articleAVendreDao, AdresseDao adresseDao, CategorieDao categorieDao) {
        this.utilisateurDao = utilisateurDao;
        this.articleAVendreDao = articleAVendreDao;
        this.adresseDao = adresseDao;
        this.categorieDao = categorieDao;

    }


    @Override
    public List<ArticleAVendre> listeArticleAVendre() {
        return articleAVendreDao.readAllEncheresActives();
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
    public long creerArticleAVendre(ArticleAVendre articleAVendre, Utilisateur vendeur, Categorie categorie) {
        articleAVendre.setVendeur(vendeur);
        articleAVendre.setCategorie(categorie);

        return articleAVendreDao.create(articleAVendre, vendeur, categorie);
    }



    @Override
    public List<ArticleAVendre> findByNomAndCategorie(String motCle, Long categorieId) {
        return articleAVendreDao.readAll().stream()
                .filter(a -> a.getNom() != null && a.getNom().toLowerCase().contains(motCle.toLowerCase()))
                .filter(a -> a.getCategorie() != null && a.getCategorie().getId() == categorieId)
                .toList();
    }

    @Override
    public List<ArticleAVendre> findByNom(String motCle) {
        return articleAVendreDao.readAll().stream()
                .filter(a -> a.getNom() != null && a.getNom().toLowerCase().contains(motCle.toLowerCase()))
                .toList();
    }

    @Override
    public List<ArticleAVendre> findByCategorie(Long categorieId) {
        return articleAVendreDao.readAll().stream()
                .filter(a -> a.getCategorie() != null && a.getCategorie().getId() == categorieId)
                .toList();
    }

    @Override
    public List<Enchere> listeEnchereParUtilisateur(Utilisateur utilisateur) {
        return enchereDao.readAllByUtilisateur(utilisateur);
    }
}
