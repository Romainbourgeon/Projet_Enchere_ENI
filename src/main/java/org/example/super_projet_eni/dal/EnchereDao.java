package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.ArticleAVendre;
import org.example.super_projet_eni.bo.Enchere;

import java.util.List;

public interface EnchereDao {

    long create (Enchere enchere);
    Enchere read (ArticleAVendre articleAVendre);
    List<Enchere> readAll ();
    void update (Enchere enchere);
    void delete (ArticleAVendre articleAVendre);

}
