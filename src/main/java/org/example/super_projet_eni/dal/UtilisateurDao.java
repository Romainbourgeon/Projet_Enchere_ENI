package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurDao {

    Utilisateur read (String pseudo);
    List<Utilisateur> readAll ();
    void update (Utilisateur utilisateur);
    void delete (String pseudo);
    Utilisateur create (Utilisateur utilisateur);
    Optional<Utilisateur> voirUtilisateurByPseudo(String pseudo);

}