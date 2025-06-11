package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.ArticleAVendre;

import java.util.List;

public interface ArticleAVendreDao {

    long create (ArticleAVendre articleAVendre);
    ArticleAVendre read (long id);
    List<ArticleAVendre> readAll ();
    void update (ArticleAVendre articleAVendre);
    void delete (long id);

}
