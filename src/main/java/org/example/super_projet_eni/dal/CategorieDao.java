package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.Categorie;
import org.example.super_projet_eni.bo.Utilisateur;

import java.util.List;

public interface CategorieDao {

    Long create (Categorie categorie);
    Categorie read (long id);
    List<Categorie> readAll ();
    void update (Categorie categorie);
    void delete (long id);

}
