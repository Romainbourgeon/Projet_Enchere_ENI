package org.example.super_projet_eni.bll;

import org.example.super_projet_eni.bo.Utilisateur;
import org.example.super_projet_eni.dal.UtilisateurDao;
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
    public void ajouterUtilisateur(Utilisateur utilisateur) {
        if (consulterUtilisateurByPseudo(utilisateur.getPseudo()) != null) {
            throw new IllegalArgumentException("Pseudo déjà utilisé."); //IllegalArgumentException : C’est une exception pré-définie dans Java,
        }                                                              //qui signifie que l’argument passé à une méthode est invalide ou interdit.
        utilisateurDao.create(utilisateur);
    }

    @Override
    public void supprimerUtilisateur(String pseudo) {
        utilisateurDao.delete(pseudo);
    }
}