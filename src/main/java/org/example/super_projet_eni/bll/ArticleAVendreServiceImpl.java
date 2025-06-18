package org.example.super_projet_eni.bll;


import org.example.super_projet_eni.bo.*;
import org.example.super_projet_eni.dal.*;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service

public class ArticleAVendreServiceImpl implements ArticleAVendreService {


    private final UtilisateurDao utilisateurDao;
    private final ArticleAVendreDao articleAVendreDao;
    private final AdresseDao adresseDao;
    private final CategorieDao categorieDao;
    private final EnchereDao enchereDao;


    public ArticleAVendreServiceImpl(UtilisateurDao utilisateurDao, ArticleAVendreDao articleAVendreDao, AdresseDao adresseDao, CategorieDao categorieDao, EnchereDao enchereDao) {
        this.utilisateurDao = utilisateurDao;
        this.articleAVendreDao = articleAVendreDao;
        this.adresseDao = adresseDao;
        this.categorieDao = categorieDao;
        this.enchereDao = enchereDao;
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

    @Override
    public List<ArticleAVendre> filtreMesVentesNonDebutees(Utilisateur utilisateur) {

        List<ArticleAVendre> tousLesArticles = articleAVendreDao.readAll();
        List<ArticleAVendre> lesArticlesByUtilisateur = tousLesArticles.stream()
                .filter(a -> a.getVendeur().getPseudo().equals(utilisateur.getPseudo())).toList();
        List<ArticleAVendre> listeFiltree = lesArticlesByUtilisateur.stream()
                .filter(a -> a.getStatut() == 0)
                .toList();

        return listeFiltree;
    }

    @Override
    public List<ArticleAVendre> filtreMesVentesTerminees(Utilisateur utilisateur) {

        List<ArticleAVendre> tousLesArticles = articleAVendreDao.readAll();
        List<ArticleAVendre> lesArticlesByUtilisateur = tousLesArticles.stream()
                .filter(a -> a.getVendeur().getPseudo().equals(utilisateur.getPseudo())).toList();
        List<ArticleAVendre> listeFiltree = lesArticlesByUtilisateur.stream()
                .filter(a -> a.getStatut() == 3)
                .toList();

        return listeFiltree;
    }

    @Override
    public List<Enchere> filtreMesEncheresEnCours(Utilisateur utilisateur) {
        List<Enchere> encheresDeUtilisateur = enchereDao.readAllByUtilisateur(utilisateur);
        List<ArticleAVendre> listeArticles = new ArrayList<>();

        encheresDeUtilisateur.stream()
                .forEach(e -> listeArticles.add(articleAVendreDao.read(e.getArticleAVendre().getId())));

        List<ArticleAVendre> listeArticleFiltree = listeArticles.stream()
                .filter(a -> a.getStatut() == 1)
                .toList();

        List<Enchere> listeFiltree = new ArrayList<>();

        /*listeArticleFiltree.forEach(a -> {
           listeFiltree.addAll(enchereDao.readAllByArticle(a));
                });*/



        listeArticleFiltree.forEach(a -> {
            encheresDeUtilisateur.forEach(e -> {
                        if (a.getId() == e.getArticleAVendre().getId()) {
                            listeFiltree.add(e);
                            e.setArticleAVendre(a);
                        }
                    }
            );
        });
        return listeFiltree;
    }

}
