package org.example.super_projet_eni.bll;


import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.Utilisateur;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UtilisateurService {

    List<Utilisateur> listeUtilisateurs();

    Utilisateur consulterUtilisateurByPseudo(String pseudo);

    void ajouterUtilisateur(Utilisateur utilisateur, Adresse adresse);

    void supprimerUtilisateur(String pseudo);

}