package org.example.super_projet_eni.bll;

import org.example.super_projet_eni.bo.Utilisateur;
import org.example.super_projet_eni.dal.UtilisateurDAO;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurDAO utilisateurDAO;


    public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    @Override
    public List<Utilisateur> listeUtilisateurs() {
        return utilisateurDAO.readAll();
    }
    @Override
    public Utilisateur consulterUtilisateurByPseudo(String pseudo) {
        return utilisateurDAO.read(pseudo);
    }


    @Override
    public void ajouterUtilisateur(Utilisateur utilisateur) {
        if (consulterUtilisateurByPseudo(utilisateur.getPseudo()) != null) {
            throw new IllegalArgumentException("Pseudo déjà utilisé."); //IllegalArgumentException : C’est une exception pré-définie dans Java,
        }                                                              //qui signifie que l’argument passé à une méthode est invalide ou interdit.
        utilisateurDAO.create(utilisateur);
    }

    @Override
    public boolean supprimerUtilisateur(String pseudo) {
        return utilisateurDAO.delete(pseudo);
    }
}
