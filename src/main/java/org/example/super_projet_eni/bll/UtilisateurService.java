package org.example.super_projet_eni.bll;


import org.example.super_projet_eni.bo.Utilisateur;
import java.util.List;

public interface UtilisateurService {

    List<Utilisateur> listeUtilisateurs();

    Utilisateur consulterUtilisateurByPseudo(String pseudo);

    void ajouterUtilisateur(Utilisateur utilisateur);

    void supprimerUtilisateur(String pseudo);

}
