package org.example.super_projet_eni.bll;

import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.Utilisateur;
import org.example.super_projet_eni.dal.AdresseDao;
import org.example.super_projet_eni.dal.EnchereDao;
import org.example.super_projet_eni.dal.UtilisateurDao;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private UtilisateurDao utilisateurDao;
    private EnchereDao enchereDao;
    private AdresseDao adresseDao;


    public UtilisateurServiceImpl(UtilisateurDao utilisateurDao, EnchereDao enchereDao, AdresseDao adresseDao) {
        this.utilisateurDao = utilisateurDao;
        this.enchereDao = enchereDao;
        this.adresseDao = adresseDao;
    }


    @Override
    public List<Utilisateur> listeUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurDao.readAll();
        return utilisateurs;
    }

    @Override
    public Optional<Utilisateur> voirUtilisateurParPseudo(String pseudo) {
        return utilisateurDao.voirUtilisateurByPseudo(pseudo);
    }

    @Override
    public Utilisateur consulterUtilisateurByPseudo(String pseudo) {
        return utilisateurDao.read(pseudo);
    }

    @Override
    public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {
        Adresse adresse = utilisateur.getAdresse();
        if (adresse != null && adresse.getId() == 0) {  // ou null selon ton type
            long idAdresse = adresseDao.create(adresse);
            adresse.setId((int) idAdresse);  // cast si adresse.getId() est int
        }
        return utilisateurDao.create(utilisateur);
    }

    @Override //NEWWWWWWWWWWWWWW
    public void update(Utilisateur utilisateur) {
        Adresse adresse = utilisateur.getAdresse();

        // Si l'adresse n'a pas d'id, on la crée en base
        if (adresse != null && adresse.getId() == 0) {
            long idAdresse = adresseDao.create(adresse);
            adresse.setId((int) idAdresse);
        }

        // Puis on met à jour l'utilisateur
        utilisateurDao.update(utilisateur);
    }


    @Override
    public List<Adresse> voirAdresses() {
        List<Adresse> adresses = adresseDao.readAll();
        return adresses;
    }

    @Override
    public Adresse voirAdresseParId(int id) {
        Adresse adresse = adresseDao.read(id);
        return adresse;
    }

    @Override
    public void creerAdresse(Adresse adresse) {
        adresseDao.create(adresse);
    }


    @Override
    public void supprimerUtilisateur(String pseudo) {
        utilisateurDao.delete(pseudo);
    }

    /*@Override //NEEEEEEEWW mais pas certain de la fonctionalité. plutot ça consulterUtilisateurByPseudo
    public Optional<Utilisateur> voirUtilisateurByPseudo(String pseudo) {
        return Optional.empty();
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return utilisateurDao.voirUtilisateurByPseudo(username)
                .map(utilisateur -> {
                    if (utilisateur.isAdmin()) {
                        utilisateur.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
                    } else {
                        utilisateur.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_USER")));
                    }
                    return utilisateur;
                })
                .orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur trouvé avec le pseudo : " + username));
    }
}


