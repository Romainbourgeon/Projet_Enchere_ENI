package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.ArticleAVendre;
import org.example.super_projet_eni.bo.Categorie;
import org.example.super_projet_eni.bo.Utilisateur;

import java.util.List;

public interface ArticleAVendreDao {

    long create (ArticleAVendre articleAVendre, Utilisateur vendeur, Categorie categorie);
    ArticleAVendre read (long id);
    List<ArticleAVendre> readAll ();
    List<ArticleAVendre> readAllEncheresActives ();
    List<ArticleAVendre> readAllByUtilisateur (Utilisateur utilisateur);
    List<ArticleAVendre> readAllEncheresActivesByCategorie (Categorie categorie);

}