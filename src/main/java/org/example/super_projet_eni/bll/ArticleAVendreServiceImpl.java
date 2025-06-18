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
    public List<ArticleAVendre> listeTousLesArticles() {
        return articleAVendreDao.readAll();
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
        articleAVendreDao.create(articleAVendre, articleAVendre.getVendeur(), articleAVendre.getCategorie());
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

    @Override
    public List<ArticleAVendre> filtreMesVentesNonDebutees(Utilisateur utilisateur) {

        List<ArticleAVendre> tousLesArticles = articleAVendreDao.readAll();
        List<ArticleAVendre> lesArticlesByUtilisateur = tousLesArticles.stream()
                .filter(a -> a.getVendeur().getPseudo().equals(utilisateur.getPseudo())).toList();
        List<ArticleAVendre> listeFiltree = lesArticlesByUtilisateur.stream()
                .filter(a -> a.getStatut() == 0)
                .toList();

        return listeFiltree ;
    }

    @Override
    public List<ArticleAVendre> filtreMesVentesTerminees(Utilisateur utilisateur) {

        List<ArticleAVendre> tousLesArticles = articleAVendreDao.readAll();
        List<ArticleAVendre> lesArticlesByUtilisateur = tousLesArticles.stream()
                .filter(a -> a.getVendeur().getPseudo().equals(utilisateur.getPseudo())).toList();
        List<ArticleAVendre> listeFiltree = lesArticlesByUtilisateur.stream()
                .filter(a -> a.getStatut() == 3)
                .toList();

        return listeFiltree ;
    }

    @Override
    public List<ArticleAVendre> filtreMesEncheresEnCours(Utilisateur utilisateur) {
        List<Enchere> encheresDeUtilisateur = enchereDao.readAllByUtilisateur(utilisateur);
        List<ArticleAVendre> listeArticles = null;

        encheresDeUtilisateur.stream()
                .forEach(e -> listeArticles.add(articleAVendreDao.read(e.getArticleAVendre().getId())));

        List<ArticleAVendre> listeFiltree = listeArticles.stream()
                .filter(a -> a.getStatut() == 1)
                .toList();

        return listeFiltree;
    }

}
