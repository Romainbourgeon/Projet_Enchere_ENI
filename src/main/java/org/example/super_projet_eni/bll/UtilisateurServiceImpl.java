package org.example.super_projet_eni.bll;

import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.Utilisateur;
import org.example.super_projet_eni.dal.UtilisateurDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurDao utilisateurDao;


    public UtilisateurServiceImpl(UtilisateurDao utilisateurDao) {
        this.utilisateurDao = utilisateurDao;
    }

    @Override
    public List<Utilisateur> listeUtilisateurs() {
        return utilisateurDao.readAll();
    }
    @Override
    public Utilisateur consulterUtilisateurByPseudo(String pseudo) {
        return utilisateurDao.read(pseudo);
    }


    @Override
    public void ajouterUtilisateur(Utilisateur utilisateur, Adresse adresse) {
        if (consulterUtilisateurByPseudo(utilisateur.getPseudo()) != null) {
            throw new IllegalArgumentException("Pseudo déjà utilisé."); //IllegalArgumentException : C’est une exception pré-définie dans Java,
        }                                                              //qui signifie que l’argument passé à une méthode est invalide ou interdit.
        utilisateurDao.create(utilisateur, adresse);
    }

    @Override
    public void supprimerUtilisateur(String pseudo) {
        utilisateurDao.delete(pseudo);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurDao.read(username);
        if (utilisateur == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + username);
        }
        // Convertir ton objet Utilisateur en UserDetails (Spring Security)
        return org.springframework.security.core.userdetails.User.builder()
                .username(utilisateur.getPseudo())
                .password(utilisateur.getMotDePasse())
                .roles(utilisateur.isAdmin() ? "ADMIN" : "USER")  // adapter selon ton rôle
                .build();
    }
}
