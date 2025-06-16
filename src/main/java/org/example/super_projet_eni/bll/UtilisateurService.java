package org.example.super_projet_eni.bll;


import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.Utilisateur;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService extends UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    List<Utilisateur> listeUtilisateurs();

    Utilisateur consulterUtilisateurByPseudo(String pseudo);

    Utilisateur ajouterUtilisateur(Utilisateur utilisateur);

    void supprimerUtilisateur(String pseudo);

    Optional<Utilisateur> voirUtilisateurParPseudo(String pseudo);
    List<Adresse> voirAdresses();
    Adresse voirAdresseParId(int id);
    void creerAdresse(Adresse adresse);
}
