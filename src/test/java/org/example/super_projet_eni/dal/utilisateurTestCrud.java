package org.example.super_projet_eni.dal;

import org.example.super_projet_eni.bo.Utilisateur;
import org.example.super_projet_eni.SuperProjetEniApplication;
import org.example.super_projet_eni.dal.UtilisateurDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest (classes = SuperProjetEniApplication.class)
public class utilisateurTestCrud {

    @Autowired
    private UtilisateurDao utilisateurDao;


    @Test
    public void testRead() {
        Utilisateur retrievedUser = utilisateurDao.read("coach_tata");
        assertNotNull(retrievedUser, "Le user récupéré ne devrait pas être null");
    }
}
