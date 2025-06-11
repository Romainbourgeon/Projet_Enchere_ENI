package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.Adresse;
import org.example.super_projet_eni.bo.Utilisateur;

import java.util.List;

public interface AdresseDao {

    long create (Adresse adresse);
    Adresse read (long id);
    List<Adresse> readAll ();
    void update (Adresse adresse);
    void delete (long id);

}
