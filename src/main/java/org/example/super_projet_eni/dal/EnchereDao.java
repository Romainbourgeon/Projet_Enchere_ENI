package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.ArticleAVendre;
import org.example.super_projet_eni.bo.Enchere;
import org.example.super_projet_eni.bo.Utilisateur;

import java.util.List;

public interface EnchereDao {

    void create (Enchere enchere, Utilisateur acquereur, ArticleAVendre articleAVendre);
    List<Enchere> readAllByUtilisateur (Utilisateur acquereur);
    List<Enchere> readAllByArticle (ArticleAVendre articleAVendre);
    // List<Enchere> readAll ();
    //void delete (ArticleAVendre articleAVendre);

    /*pas de fonction update car ça ne semble pas pertinent pour les enchères.
    On pourra éventuellement supprimer une enchère et en recréer une.*/


}
