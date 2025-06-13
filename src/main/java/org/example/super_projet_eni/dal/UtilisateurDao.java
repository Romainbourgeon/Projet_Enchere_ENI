package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.Utilisateur;

import java.util.List;

public interface UtilisateurDao {

    void create (Utilisateur utilisateur, Adresse adresse);
    Utilisateur read (String pseudo);
    List<Utilisateur> readAll ();
    void update (Utilisateur utilisateur);
    void delete (String pseudo);

}
