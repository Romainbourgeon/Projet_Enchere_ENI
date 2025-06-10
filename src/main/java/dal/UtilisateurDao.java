package dal;

import bo.Utilisateur;

import java.util.List;

public interface UtilisateurDao {

    void create (Utilisateur utilisateur);
    Utilisateur read (String pseudo);
    List<Utilisateur> readAll ();
    void update (Utilisateur utilisateur);
    void delete (String pseudo);

}
